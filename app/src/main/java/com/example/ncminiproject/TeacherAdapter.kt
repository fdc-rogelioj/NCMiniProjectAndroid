package com.example.ncminiproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class TeacherAdapter(private val teachers: List<Teacher>,
                     private val onItemClick: (Teacher) -> Unit
) : RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder>() {

    inner class TeacherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.teacherName)
        val countryTextView: TextView = view.findViewById(R.id.teacherCountry)
        val countryImageView: ImageView = view.findViewById(R.id.countryImage)
        val ageTextView: TextView = view.findViewById(R.id.teacherAge)
        val ratingTextView: TextView = view.findViewById(R.id.teacherRating)
        val lessonsTextView: TextView = view.findViewById(R.id.teacherLessons)
        val coinTextView: TextView = view.findViewById(R.id.teacherCoin)
        val imageView: ImageView = view.findViewById(R.id.teacherImage)
        val favoriteCountTextView: TextView = view.findViewById(R.id.teacherFavoriteCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_teacher, parent, false)
        return TeacherViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeacherViewHolder, position: Int) {
        val teacher = teachers[position]
        holder.nameTextView.text = teacher.name
        holder.countryTextView.text = teacher.countryName

        // Load the country image into countryImageView
        Glide.with(holder.itemView.context)
            .load(teacher.countryImageFlag)
            .placeholder(R.drawable.placeholder_image) // Replace with your placeholder image
            .into(holder.countryImageView)

        holder.ageTextView.text = "(Age: ${teacher.age})"
        holder.ratingTextView.text = "${teacher.rating}"
        holder.lessonsTextView.text = "${teacher.lessons}"
        holder.coinTextView.text = "${teacher.coin}"
        holder.favoriteCountTextView.text = "${teacher.favoriteCount}"

        // Load the imageMain into ImageView
        Glide.with(holder.itemView.context)
            .load(teacher.imageMain)
            .placeholder(R.drawable.placeholder_image)
            .into(holder.imageView)

        // Set click listener
        holder.itemView.setOnClickListener {
            onItemClick(teacher)
        }
    }

    override fun getItemCount(): Int = teachers.size
}
