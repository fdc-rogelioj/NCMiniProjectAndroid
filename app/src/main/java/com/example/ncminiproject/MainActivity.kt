package com.example.ncminiproject

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set the status bar color
        window.statusBarColor = getColor(R.color.colorBlack)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.apply {
                setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    )
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    window.decorView.systemUiVisibility and
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                    )
        }

        // Initialize TabLayout
        tabLayout = findViewById(R.id.tabLayout)

        // Set tab selection listener
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> showFragment(TeacherListFragment())  // LESSONS tab
                    1 -> showFragment(NcPlusFragment())       // NC+ tab
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // Show the default fragment (TeacherListFragment) initially
        if (savedInstanceState == null) {
            showFragment(TeacherListFragment())
        }

        onBackPressedDispatcher.addCallback(this) {
            if (supportFragmentManager.backStackEntryCount == 0) {
                // This is the root of the back stack, show the TabLayout
                tabLayout.visibility = View.VISIBLE
            } else {
                // Check if the current fragment is TeacherDetailsFragment
                val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
                if (currentFragment is TeacherDetailsFragment) {
                    // Show TabLayout when returning to TeacherListFragment
                    tabLayout.visibility = View.VISIBLE
                }
                supportFragmentManager.popBackStack()
            }
        }

    }

    private fun showFragment(fragment: Fragment) {
        // Make the tab layout visible when switching between main fragments
        tabLayout.visibility = View.VISIBLE

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    fun showTeacherDetails(
        id: Int,
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
    ) {
        // Hide the tab layout when showing TeacherDetailsFragment
        tabLayout.visibility = View.GONE

        val fragment = TeacherDetailsFragment.newInstance(
            name,
            age,
            imageUrl,
            iconUrl,
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

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun showTabLayout() {
        tabLayout.visibility = View.VISIBLE
    }

}
