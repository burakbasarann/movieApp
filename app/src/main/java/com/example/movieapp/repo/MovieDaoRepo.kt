package com.example.movieapp.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.movieapp.data.remote.APIService
import com.example.movieapp.model.Movies
import com.example.movieapp.model.RoomDBEntity
import javax.inject.Inject


class MovieDaoRepo @Inject constructor(private val mDao: APIService) {
    private var movieList: MutableLiveData<List<Movies>>

    init {
        movieList = MutableLiveData()
    }

    fun getMovieList(): MutableLiveData<List<Movies>> {
        return movieList
    }



    suspend fun allMovies(){
        val response = mDao.getGenresAndMoviesList()
        if(response.isSuccessful){
            response.body()?.let {
                Log.e("api1","girdi")
                val list = response.body()
                movieList.value = list?.movies
            }

        }else{
            Log.e("api1","hata")
        }
    }
}