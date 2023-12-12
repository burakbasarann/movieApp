package com.example.movieapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.Movies
import com.example.movieapp.model.RoomDBEntity
import com.example.movieapp.repo.MovieDBRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoriViewModel @Inject constructor(private val roomDBRepo: MovieDBRepo) : ViewModel()  {
    var yemeklerFavoriListesi = MutableLiveData<MutableList<RoomDBEntity>>()

    init {
        favoriYemekleriYukle()
        yemeklerFavoriListesi = roomDBRepo.favorileriGetir()
    }

    fun favoriYemekleriYukle(){
        roomDBRepo.tumFavoriFilmleriAl()
    }

    fun insertMovies(roomDBEntity: RoomDBEntity){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                roomDBRepo.insertMovies(roomDBEntity)
            }
        }
    }

    fun deleteMovies(roomDBEntity: RoomDBEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                roomDBRepo.deleteMovies(roomDBEntity)
            }
        }
    }
}