package com.example.movieapp.data.remote

import com.example.movieapp.model.GenresMovies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface APIService {


    @GET("db.json")
    suspend fun getGenresAndMoviesList() : Response<GenresMovies>
}