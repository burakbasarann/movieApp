package com.example.movieapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.movieapp.model.Movies
import com.example.movieapp.model.RoomDBEntity
import java.util.concurrent.Flow

@Dao
interface RoomDBDao {
    @Insert
    fun insert(entity: RoomDBEntity)

    @Query("SELECT * FROM favori")
    fun getAllEntities(): MutableList<RoomDBEntity>

    @Delete
    fun delete(entityDelete: RoomDBEntity)

    // Diğer sorgular ve işlemler
}