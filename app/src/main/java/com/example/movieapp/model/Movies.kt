package com.example.movieapp.model

import com.google.gson.annotations.SerializedName

data class Movies(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("year")
    val year: String?,
    @SerializedName("runtime")
    val runtime: String?,
    @SerializedName("genres")
    val genres: List<String>?,
    @SerializedName("director")
    val director: String?,
    @SerializedName("actors")
    val actors: String?,
    @SerializedName("plot")
    val plot: String?,
    @SerializedName("posterUrl")
    val posterUrl: String?
)
