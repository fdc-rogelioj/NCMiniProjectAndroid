package com.example.ncminiproject

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TeacherListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var teacherAdapter: TeacherAdapter
    private lateinit var progressBar: ProgressBar

    private lateinit var buttonRatingsOrder: TextView
    private lateinit var buttonKidsRatingsOrder: TextView
    private lateinit var buttonLessonCountOrder: TextView
    private lateinit var buttonFavoriteCountOrder: TextView
    private lateinit var buttonTutorHistoryOrder: TextView
    private lateinit var buttonNativeOrder: TextView
    private lateinit var horizontalScrollView: HorizontalScrollView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_teacher_list, container, false)

        // Initialize buttons
        buttonRatingsOrder = view.findViewById(R.id.buttonRatingsOrder)
        buttonKidsRatingsOrder = view.findViewById(R.id.buttonKidsRatingsOrder)
        buttonLessonCountOrder = view.findViewById(R.id.buttonLessonCountOrder)
        buttonFavoriteCountOrder = view.findViewById(R.id.buttonFavoriteCountOrder)
        buttonTutorHistoryOrder = view.findViewById(R.id.buttonTutorHistoryOrder)
        buttonNativeOrder = view.findViewById(R.id.buttonNativeOrder)
        horizontalScrollView = view.findViewById(R.id.horizontalScrollViewOrderButtons)
        progressBar = view.findViewById(R.id.progressBar)

        // Set click listeners for buttons
        buttonRatingsOrder.setOnClickListener { onButtonClicked(buttonRatingsOrder) }
        buttonKidsRatingsOrder.setOnClickListener { onButtonClicked(buttonKidsRatingsOrder) }
        buttonLessonCountOrder.setOnClickListener { onButtonClicked(buttonLessonCountOrder) }
        buttonFavoriteCountOrder.setOnClickListener { onButtonClicked(buttonFavoriteCountOrder) }
        buttonTutorHistoryOrder.setOnClickListener { onButtonClicked(buttonTutorHistoryOrder) }
        buttonNativeOrder.setOnClickListener { onButtonClicked(buttonNativeOrder) }

        // Set default button
        setDefaultButton()

        // Set click listener for Home button (same behavior as buttonRatingsOrder)
        val buttonHome: ImageButton = view.findViewById(R.id.buttonHome)
        buttonHome.setOnClickListener {
            onButtonClicked(buttonRatingsOrder)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewTeachers)
        recyclerView.layoutManager = LinearLayoutManager(context)

        fetchTeachers()

        // Set up HorizontalScrollView listener
        horizontalScrollView.setOnScrollChangeListener { _, scrollX, _, oldScrollX, _ ->
            if (scrollX > oldScrollX) {
                // Scrolling right
                horizontalScrollView.isVerticalScrollBarEnabled = false
                horizontalScrollView.isHorizontalScrollBarEnabled = false
            } else if (scrollX < oldScrollX) {
                // Scrolling left
                horizontalScrollView.isVerticalScrollBarEnabled = false
                horizontalScrollView.isHorizontalScrollBarEnabled = false
            }
        }
    }

    private fun setDefaultButton() {
        // Highlight the default button (Ratings Order)
        highlightButton(buttonRatingsOrder)
        scrollToCenter(buttonRatingsOrder)
    }

    private fun onButtonClicked(button: TextView) {
        highlightButton(button)
        scrollToCenter(button)
        fetchTeachers() // Fetch data after button click
    }

    private fun highlightButton(selectedButton: TextView) {
        val buttons = listOf(
            buttonRatingsOrder, buttonKidsRatingsOrder, buttonLessonCountOrder,
            buttonFavoriteCountOrder, buttonTutorHistoryOrder, buttonNativeOrder
        )

        // Reset background and text color for all buttons
        buttons.forEach { button ->
            button.setBackgroundResource(R.drawable.button_unselected_background) // Set transparent background
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorOnPrimary)) // White text color
        }

        // Set the background and text color for the selected button
        selectedButton.setBackgroundResource(R.drawable.button_selected_background) // White background with rounded edges
        selectedButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorBlack)) // Black text color
    }

    private fun scrollToCenter(button: TextView) {
        val buttonLeft = button.left
        val buttonWidth = button.width
        val scrollViewWidth = horizontalScrollView.width
        val scrollViewLeft = horizontalScrollView.scrollX

        // Calculate the scroll position to center the button
        val scrollX = buttonLeft - (scrollViewWidth / 2) + (buttonWidth / 2)
        horizontalScrollView.smoothScrollBy(scrollX - scrollViewLeft, 0)
    }

    private fun fetchTeachers() {
        Log.d("TeacherListFragment", "Fetching teachers")

        progressBar.visibility = View.VISIBLE // Show ProgressBar

        ApiService.getTeachers(requireContext(),
            onSuccess = { teachers ->
                Log.d("TeacherListFragment", "Fetched ${teachers.size} teachers")
                teacherAdapter = TeacherAdapter(teachers) { teacher ->
                    // When a teacher is clicked, fetch their details
                    fetchTeacherDetails(teacher.id)
                }
                recyclerView.adapter = teacherAdapter
                progressBar.visibility = View.GONE // Hide ProgressBar
            },
            onError = { errorMessage ->
                Log.e("TeacherListFragment", "Error fetching teachers: $errorMessage")
                progressBar.visibility = View.GONE // Hide ProgressBar
            }
        )
    }

    private fun fetchTeacherDetails(teacherId: Int) {
        Log.d("TeacherListFragment", "Fetching details for teacher ID: $teacherId")

        ApiService.fetchTeacherDetails(requireContext(), teacherId,
            onSuccess = { response ->
                Log.d("TeacherDetails", "Teacher details fetched: $response")
                val jsonObject = response.getJSONObject("teacher")
                val imagesObject = jsonObject.getJSONObject("images")

                // Retrieve the images JSON object and get the main image URL
                val mainImageUrl = imagesObject.optString("main", "")

                val name = jsonObject.getString("name_eng")
                val age = jsonObject.getInt("age")
                val countryImageUrl = jsonObject.getString("country_image")
                val countryFlagName = jsonObject.getString("country_name")
                val teacherHistory = jsonObject.getString("instructor_history")
                val teacherRating = jsonObject.optDouble("rating", 0.00)
                val teacherKidsRating = jsonObject.optDouble("kids_rating", 0.00)
                val teacherLessons = jsonObject.optInt("lessons", 0)
                val teacherFavoriteCount = jsonObject.optInt("favorite_count", 0)
                val teacherLastLoginDate = jsonObject.getString("last_login_date")
                val teacherMessageProfile = jsonObject.getString("message")
                val teacherReservationCoin = jsonObject.getInt("coin_reservation")
                val teacherSuddenLessonCoin = jsonObject.getString("coin_lesson_now")
                val teacherLessonDetailsCount = jsonObject.getInt("lesson_now_count")
                val teacherLessonDetailsReserveCount = jsonObject.getInt("reservation_lesson_count")
                val teacherEvalRatings = jsonObject.optDouble("rating", 0.00)

                // Fetch features array safely
                val featuresArray = jsonObject.optJSONArray("features")
                val teacherFeatures = mutableListOf<String>()
                if (featuresArray != null) {
                    for (i in 0 until featuresArray.length()) {
                        teacherFeatures.add(featuresArray.getString(i))
                    }
                }

                // Fetch hobbies array safely
                val hobbiesArray = jsonObject.optJSONArray("hobbies")
                val teacherHobbies = mutableListOf<String>()
                if (hobbiesArray != null) {
                    for (i in 0 until hobbiesArray.length()) {
                        teacherHobbies.add(hobbiesArray.getString(i))
                    }
                }

                // Call MainActivity method to show teacher details
                (activity as? MainActivity)?.showTeacherDetails(
                    teacherId,
                    name,
                    age,
                    mainImageUrl,
                    mainImageUrl,
                    countryImageUrl,
                    countryFlagName,
                    teacherHistory,
                    teacherRating,
                    teacherKidsRating,
                    teacherLessons,
                    teacherFavoriteCount,
                    teacherLastLoginDate,
                    teacherMessageProfile,
                    teacherReservationCoin,
                    teacherSuddenLessonCoin,
                    teacherFeatures,
                    teacherHobbies,
                    teacherLessonDetailsCount,
                    teacherLessonDetailsReserveCount,
                    teacherEvalRatings
                )
            },
            onError = { error ->
                Log.e("TeacherDetails", "Error fetching details: $error")
            }
        )
    }

}
