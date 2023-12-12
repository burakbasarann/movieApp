package com.example.movieapp.repo

import androidx.lifecycle.MutableLiveData
import com.example.movieapp.data.local.RoomDBDao
import com.example.movieapp.model.RoomDBEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class MovieDBRepo @Inject constructor(private val appDao: RoomDBDao) {
    private var favoriYemeklerListesi: MutableLiveData<MutableList<RoomDBEntity>> = MutableLiveData()


    fun favorileriGetir(): MutableLiveData<MutableList<RoomDBEntity>> {
        return favoriYemeklerListesi
    }
    fun tumFavoriFilmleriAl() {
        val job = CoroutineScope(Dispatchers.Main).launch {
            favoriYemeklerListesi.value = appDao.getAllEntities()
        }
    }

    fun insertMovies(roomDBEntity: RoomDBEntity){
        return appDao.insert(roomDBEntity)
    }

    fun deleteMovies(roomDBEntity: RoomDBEntity){
        return appDao.delete(entityDelete = roomDBEntity)
    }
}