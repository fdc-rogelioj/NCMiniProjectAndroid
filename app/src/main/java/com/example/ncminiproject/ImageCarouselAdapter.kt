package com.example.ncminiproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.imageview.ShapeableImageView

class ImageCarouselAdapter(private var imageUrls: List<String>) :
    RecyclerView.Adapter<ImageCarouselAdapter.CarouselViewHolder>() {

    fun setImages(images: List<String>) {
        this.imageUrls = images
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.carousel_item, parent, false) // Use your carousel_item layout
        return CarouselViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val imageUrl = imageUrls[position]
        Glide.with(holder.imageView.context)
            .load(imageUrl)
            .apply(RequestOptions().centerCrop())  // Apply image options like cropping
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = imageUrls.size

    class CarouselViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ShapeableImageView = view.findViewById(R.id.imageView)
    }
}


