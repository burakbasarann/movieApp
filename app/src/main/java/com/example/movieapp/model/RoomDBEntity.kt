package com.example.movieapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "favori")
data class RoomDBEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val title: String?,
    val year: String?,
    val runtime: String?,
   // val genres: List<String> = listOf(""),
    val director: String?,
    val actors: String?,
    val plot: String?,
    val posterUrl: String?
)
