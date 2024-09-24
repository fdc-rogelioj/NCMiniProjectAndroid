package com.example.ncminiproject

import com.google.gson.annotations.SerializedName

data class Teacher(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("age") val age: Int,
    @SerializedName("rating") val rating: String,
    @SerializedName("lessons") val lessons: Int,
    @SerializedName("coin") val coin: Int,
    @SerializedName("favorite_count") val favoriteCount: Int,
    @SerializedName("image_main") val imageMain: String,
    @SerializedName("country_image") val countryImageFlag: String,
    @SerializedName("country_name") val countryName: String
)
