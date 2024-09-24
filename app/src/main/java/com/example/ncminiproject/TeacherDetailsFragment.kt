package com.example.ncminiproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TeacherDetailsFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_teacher_details, container, false)

        tabLayout = view.findViewById(R.id.tutorsTabLayout)
        viewPager = view.findViewById(R.id.viewPager)

        setupViewPager(viewPager)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "TUTOR'S PROFILE"
                1 -> "LESSON DETAILS"
                2 -> "REVIEWS"
                else -> null
            }
        }.attach()

        // Get views
        val teacherName = view.findViewById<TextView>(R.id.detailTeacherName)
        val teacherAge = view.findViewById<TextView>(R.id.detailTeacherAge)
        val teacherImage = view.findViewById<ImageView>(R.id.detailTeacherImage)
        val teacherIcon = view.findViewById<ImageView>(R.id.teacherIcon)
        val countryImage = view.findViewById<ImageView>(R.id.countryImage)
        val countryName = view.findViewById<TextView>(R.id.teacherCountry)
        val teacherHistory = view.findViewById<TextView>(R.id.teacherHistory)
        val teacherRating = view.findViewById<TextView>(R.id.teacherRating)
        val teacherKidsRating = view.findViewById<TextView>(R.id.teacherKidsRating)
        val teacherLessons = view.findViewById<TextView>(R.id.teacherLessons)
        val teacherFavoriteCount = view.findViewById<TextView>(R.id.teacherFavoriteCount)
        val teacherLastLoginDate = view.findViewById<TextView>(R.id.teacherLastLoginDate)
        val backArrow = view.findViewById<ImageView>(R.id.backArrow)

        // Retrieve data from arguments
        val name = arguments?.getString("TEACHER_NAME")
        val age = arguments?.getInt("TEACHER_AGE")
        val imageUrl = arguments?.getString("TEACHER_IMAGE")
        val iconUrl = arguments?.getString("TEACHER_ICON")
        val countryImageUrl = arguments?.getString("TEACHER_COUNTRY_IMAGE")
        val countryFlagName = arguments?.getString("TEACHER_COUNTRY_NAME")
        val teacherHistoryView = arguments?.getString("TEACHER_HISTORY")
        val teacherRatingView = arguments?.getDouble("TEACHER_RATING")
        val teacherKidsRatingView = arguments?.getDouble("TEACHER_KIDS_RATING")
        val teacherLessonsView = arguments?.getInt("TEACHER_LESSONS")
        val teacherFavoriteCountView = arguments?.getInt("TEACHER_FAVORITES_COUNT")
        val teacherLastLoginDateView = arguments?.getString("TEACHER_LAST_LOGIN_DATE")

        // Set data to views
        teacherName.text = name
        teacherAge.text = "(Age: $age)"
        countryName.text = countryFlagName
        teacherHistory.text = teacherHistoryView
        teacherRating.text = teacherRatingView.toString()
        teacherKidsRating.text = teacherKidsRatingView.toString()
        teacherLessons.text = teacherLessonsView.toString()
        teacherFavoriteCount.text = teacherFavoriteCountView.toString()
        teacherLastLoginDate.text = teacherLastLoginDateView

        // Load image using Glide or similar library
        Glide.with(this).load(imageUrl).into(teacherImage)
        Glide.with(this).load(iconUrl).apply(RequestOptions.circleCropTransform()).into(teacherIcon)
        Glide.with(this).load(countryImageUrl).into(countryImage)

        // Set back button click listener
        backArrow.setOnClickListener {
            // Pop the fragment and ensure the tab layout remains visible
            requireActivity().supportFragmentManager.popBackStack()
            (requireActivity() as MainActivity).showTabLayout() // Show the tab layout
        }

        return view
    }

    private fun setupViewPager(viewPager: ViewPager2) {
        val adapter = ViewPagerAdapter(this)

        // Use the arguments to create the tutorProfileFragment
        val hobbiesArray = arguments?.getStringArrayList("TEACHER_HOBBIES") ?: listOf()
        val featuresArray = arguments?.getStringArrayList("TEACHER_FEATURES") ?: listOf()
        val tutorProfileFragment = TutorProfileFragment.newInstance(
            message = arguments?.getString("TEACHER_MESSAGE_PROFILE") ?: "",
            reservationCoin = arguments?.getInt("RESERVATION_COIN") ?: 0, // Provide default value
            suddenCoin = arguments?.getString("SUDDEN_COIN") ?: "",
            hobbies = hobbiesArray,
            features = featuresArray
        )

        // Create LessonDetailsFragment instance
        val lessonDetailsFragment = LessonDetailsFragment.newInstance(
            tutorSuddenLessonCoin = arguments?.getInt("TEACHER_LESSON_DETAILS_COUNT") ?: 0,
            tutorReserveLessonCoin = arguments?.getInt("TEACHER_LESSON_DETAILS_RESERVE_COUNT") ?: 0
        )

        // Create LessonDetailsFragment instance
        val reviewsFragment = ReviewsFragment.newInstance(
            teacherEvalRatings = arguments?.getDouble("TEACHER_EVAL_RATINGS") ?: 0.0
        )

        // Add the correctly initialized fragment to the adapter
        adapter.addFragment(tutorProfileFragment)
        adapter.addFragment(lessonDetailsFragment)
        adapter.addFragment(reviewsFragment)

        viewPager.adapter = adapter
    }


    inner class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        private val fragmentList: MutableList<Fragment> = mutableListOf()

        override fun getItemCount(): Int = fragmentList.size

        override fun createFragment(position: Int): Fragment = fragmentList[position]

        fun addFragment(fragment: Fragment) {
            fragmentList.add(fragment)
        }
    }

    companion object {
        fun newInstance(
            name: String,
            age: Int,
            imageUrl: String,
            iconUrl: String,
            countryImageUrl: String,
            countryFlagName: String,
            teacherHistory: String,
            teacherRating: Double,
            teacherKidsRating: Double,
            teacherLessons: Int,
            teacherFavoriteCount: Int,
            teacherLastLoginDate: String,
            teacherMessageProfile: String,
            teacherReservationCoin: Int,
            teacherSuddenLessonCoin: String,
            teacherFeatures: List<String>,
            teacherHobbies: List<String>,
            teacherLessonDetailsCount: Int,
            teacherLessonDetailsReserveCount: Int,
            teacherEvalRatings: Double
        ): TeacherDetailsFragment {
            val fragment = TeacherDetailsFragment()
            val args = Bundle()
            args.putString("TEACHER_NAME", name)
            args.putInt("TEACHER_AGE", age)
            args.putString("TEACHER_IMAGE", imageUrl)
            args.putString("TEACHER_ICON", iconUrl)
            args.putString("TEACHER_COUNTRY_IMAGE", countryImageUrl)
            args.putString("TEACHER_COUNTRY_NAME", countryFlagName)
            args.putString("TEACHER_HISTORY", teacherHistory)
            args.putDouble("TEACHER_RATING", teacherRating)
            args.putDouble("TEACHER_KIDS_RATING", teacherKidsRating)
            args.putInt("TEACHER_LESSONS", teacherLessons)
            args.putInt("TEACHER_FAVORITES_COUNT", teacherFavoriteCount)
            args.putString("TEACHER_LAST_LOGIN_DATE", teacherLastLoginDate)
            args.putString("TEACHER_MESSAGE_PROFILE", teacherMessageProfile)
            args.putInt("RESERVATION_COIN", teacherReservationCoin)
            args.putString("SUDDEN_COIN", teacherSuddenLessonCoin)
            args.putStringArrayList("TEACHER_FEATURES", teacherFeatures as ArrayList<String>)
            args.putStringArrayList("TEACHER_HOBBIES", teacherHobbies as ArrayList<String>)
            args.putInt("TEACHER_LESSON_DETAILS_COUNT", teacherLessonDetailsCount)
            args.putInt("TEACHER_LESSON_DETAILS_RESERVE_COUNT", teacherLessonDetailsReserveCount)
            args.putDouble("TEACHER_EVAL_RATINGS", teacherEvalRatings)
            fragment.arguments = args
            return fragment
        }
    }
}
