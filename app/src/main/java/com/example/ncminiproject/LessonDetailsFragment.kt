package com.example.ncminiproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class LessonDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_lesson_details, container, false)

        val tutorLessonDetailCount = arguments?.getInt("TEACHER_LESSON_DETAILS_COUNT") ?: 0
        val tutorLessonDetailReserveCount = arguments?.getInt("TEACHER_LESSON_DETAILS_RESERVE_COUNT") ?: 0

        val tutorSuddenLessonCountView = view.findViewById<TextView>(R.id.suddenLessonDetails)
        val tutorReserveLessonCoinView = view.findViewById<TextView>(R.id.bookedLessonDetails)

        // Retrieve data from arguments
        tutorSuddenLessonCountView.text = "Start Sudden Lesson : $tutorLessonDetailCount"
        tutorReserveLessonCoinView.text = "Booked Lesson : $tutorLessonDetailReserveCount"

        return view
    }

    companion object {
        fun newInstance(tutorSuddenLessonCoin: Int, tutorReserveLessonCoin: Int): LessonDetailsFragment {
            val fragment = LessonDetailsFragment()
            val args = Bundle()
            args.putInt("TEACHER_LESSON_DETAILS_COUNT", tutorSuddenLessonCoin)
            args.putInt("TEACHER_LESSON_DETAILS_RESERVE_COUNT", tutorReserveLessonCoin)
            fragment.arguments = args
            return fragment
        }
    }
}
