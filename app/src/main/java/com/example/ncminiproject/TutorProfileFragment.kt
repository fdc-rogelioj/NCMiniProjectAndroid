package com.example.ncminiproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class TutorProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tutor_profile, container, false)

        val tutorMessage = arguments?.getString("TUTOR_MESSAGE")
        val reservationCoin = arguments?.getInt("RESERVATION_COIN")
        val suddenLessonCoin = arguments?.getString("SUDDEN_COIN")
        val hobbiesList = arguments?.getStringArrayList("HOBBIES_PROFILE")
        val featuresList = arguments?.getStringArrayList("FEATURES_PROFILE")

        val messageTextView = view.findViewById<TextView>(R.id.tutorMessageProfile)
        val lessonCoinTextView = view.findViewById<TextView>(R.id.startSuddenLesson)
        val reservationCoinTextView = view.findViewById<TextView>(R.id.bookedLesson)
        val hobbiesTextView = view.findViewById<TextView>(R.id.hobbiesProfile)
        val featuresTextView = view.findViewById<TextView>(R.id.featuresProfile)

        // Retrieve data from arguments
        messageTextView.text = tutorMessage
        reservationCoinTextView.text = "Booked Lesson: $reservationCoin coins"
        lessonCoinTextView.text = if (suddenLessonCoin == "0") {
            "Start Sudden Lesson: No Coins Required"
        } else {
            "Start Sudden Lesson: $suddenLessonCoin"
        }

        // Join hobbies into a single string
        val hobbiesString = hobbiesList?.joinToString(", ") ?: ""
        hobbiesTextView.text = "$hobbiesString"

        // Join features into a single string
        val featuresString = featuresList?.joinToString(", ") ?: ""
        featuresTextView.text = "$featuresString"

        return view
    }

    companion object {
        fun newInstance(message: String, reservationCoin: Int, suddenCoin: String, hobbies: List<String>, features: List<String>): TutorProfileFragment {
            val fragment = TutorProfileFragment()
            val args = Bundle()
            args.putString("TUTOR_MESSAGE", message)
            args.putInt("RESERVATION_COIN", reservationCoin)
            args.putString("SUDDEN_COIN", suddenCoin)
            args.putStringArrayList("HOBBIES_PROFILE", ArrayList(hobbies))
            args.putStringArrayList("FEATURES_PROFILE", ArrayList(features))
            fragment.arguments = args
            return fragment
        }
    }
}