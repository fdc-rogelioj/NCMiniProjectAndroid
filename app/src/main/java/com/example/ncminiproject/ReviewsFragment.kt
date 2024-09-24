package com.example.ncminiproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ReviewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_review, container, false)

        // Retrieve data from arguments
        val teacherEvalRatings = arguments?.getDouble("TEACHER_EVAL_RATINGS") ?: 0.0
        val teacherEvalRatingsView = view.findViewById<TextView>(R.id.teacherEvalRatings)

        // Set the text for the TextView
        teacherEvalRatingsView.text = "$teacherEvalRatings"

        return view
    }

    companion object {
        fun newInstance(teacherEvalRatings: Double): ReviewsFragment {
            val fragment = ReviewsFragment()
            val args = Bundle()
            args.putDouble("TEACHER_EVAL_RATINGS", teacherEvalRatings)
            fragment.arguments = args
            return fragment
        }
    }
}
