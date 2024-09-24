package com.example.ncminiproject

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.coroutines.*

class NcPlusFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ImageCarouselAdapter
    private var autoScrollJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nc_plus, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = view.findViewById(R.id.viewPagerNcPlus)
        adapter = ImageCarouselAdapter(emptyList())
        viewPager.adapter = adapter

        fetchImageUrls() // Fetch image URLs
    }

    private fun fetchImageUrls() {
        val url = "https://english-staging.fdc-inc.com/api/teachers/search?src_view=home_teacher"

        val jsonRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                Log.d("NcPlusFragment", "Response: $response")
                val imageUrls = mutableListOf<String>()
                try {
                    val teachersArray = response.getJSONArray("teachers")
                    for (i in 0 until teachersArray.length()) {
                        val teacherObject = teachersArray.getJSONObject(i)
                        val imageUrl = teacherObject.getString("image_main")
                        imageUrls.add(imageUrl)
                    }
                    Log.d("NcPlusFragment", "Image URLs: $imageUrls")
                    adapter.setImages(imageUrls)
                    startAutoScroll(imageUrls.size)
                } catch (e: Exception) {
                    Log.e("NcPlusFragment", "Error parsing response: ${e.message}")
                }
            },
            { error ->
                Log.e("NcPlusFragment", "Error fetching images: ${error.message}")
            }
        )

        ApiClient.getRequestQueue(requireContext()).add(jsonRequest)
    }

    private fun startAutoScroll(imageCount: Int) {
        if (imageCount == 0) return // Prevent starting scroll with no images
        autoScrollJob = CoroutineScope(Dispatchers.Main).launch {
            var currentIndex = 0
            while (true) {
                delay(5000)
                currentIndex = (currentIndex + 1) % imageCount
                viewPager.setCurrentItem(currentIndex, true)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        autoScrollJob?.cancel() // Cancel the job when the view is destroyed
    }

}
