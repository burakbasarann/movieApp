package com.example.movieapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieapp.model.RoomDBEntity

@Database(entities = [RoomDBEntity::class], version = 2)
abstract class VeritabaniFavori : RoomDatabase() {

    abstract fun yemeklerFavoriDao(): RoomDBDao

}