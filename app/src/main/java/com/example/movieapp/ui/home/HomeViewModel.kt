package com.example.movieapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.Movies
import com.example.movieapp.model.RoomDBEntity
import com.example.movieapp.repo.MovieDBRepo
import com.example.movieapp.repo.MovieDaoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val mRepo: MovieDaoRepo, private val dbRepo: MovieDBRepo) : ViewModel() {
    private var _moviesList = MutableLiveData<List<Movies>>()
    val moviesList: LiveData<List<Movies>> get() = _moviesList
    private val _yourEntities = MutableLiveData<List<RoomDBEntity>>()
    val yourEntities: LiveData<List<RoomDBEntity>> get() = _yourEntities

    init{
        getMoviesList()
        _moviesList = mRepo.getMovieList()
    }

    private fun getMoviesList()=viewModelScope.launch{
        mRepo.allMovies()
    }

}