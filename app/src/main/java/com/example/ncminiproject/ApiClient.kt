package com.example.ncminiproject

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

object ApiClient {
    fun getRequestQueue(context: Context): RequestQueue =
        Volley.newRequestQueue(context.applicationContext)
}