package com.example.ncminiproject

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

object ApiService {
    private const val TEACHER_DETAIL_URL = "https://english-staging.fdc-inc.com/api/teachers/detail"
    private const val TEACHERS_URL = "https://english-staging.fdc-inc.com/api/teachers/search?src_view=home_teacher"

    fun fetchTeacherDetails(
        context: Context, teacherId: Int,
        onSuccess: (JSONObject) -> Unit,
        onError: (String) -> Unit
    ) {
        val jsonRequest = JSONObject().apply {
            put("users_api_token", "b1c620715abe49b8ee506d9df480cd1b")
            put("teachers_id", teacherId)
            put("check_sapuri_user", false)
            put("emergency_lesson", 0)
            put("api_version", 30)
            put("app_version", "5.2.5")
            put("device_type", 2)
        }

        val request = JsonObjectRequest(
            Request.Method.POST, TEACHER_DETAIL_URL, jsonRequest,
            { response -> onSuccess(response) },
            { error ->
                Log.e("ApiService", "Error fetching teacher details: ${error.message}, response: ${String(error.networkResponse.data)}", error)
            }
        )

        ApiClient.getRequestQueue(context).add(request)
    }

    fun getTeachers(
        context: Context,
        onSuccess: (List<Teacher>) -> Unit,
        onError: (String) -> Unit
    ) {
        val request = JsonObjectRequest(
            Request.Method.GET, TEACHERS_URL, null,
            { response ->
                try {
                    val teachersArray = response.getJSONArray("teachers")
                    val teachers = mutableListOf<Teacher>()

                    for (i in 0 until teachersArray.length()) {
                        val teacherJson = teachersArray.getJSONObject(i)
                        val teacher = Teacher(
                            id = teacherJson.getInt("id"),
                            name = teacherJson.getString("name_eng"),
                            age = teacherJson.getInt("age"),
                            rating = teacherJson.getString("rating"),
                            lessons = teacherJson.getInt("lessons"),
                            coin = teacherJson.getInt("coin"),
                            favoriteCount = teacherJson.getInt("favorite_count"),
                            imageMain = teacherJson.getString("image_main"),
                            countryImageFlag = teacherJson.getString("country_image"),
                            countryName = teacherJson.getString("country_name")
                        )
                        teachers.add(teacher)
                    }

                    onSuccess(teachers)
                } catch (e: Exception) {
                    onError("Error parsing response: ${e.message}")
                }
            },
            { error ->
                Log.e("ApiService", "Error fetching teacher details: ${error.message}", error)
                onError(error.message ?: "Error occurred")
            }
        )

        ApiClient.getRequestQueue(context).add(request)
    }
}