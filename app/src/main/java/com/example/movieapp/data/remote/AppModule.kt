package com.example.movieapp.data.remote

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.movieapp.data.local.RoomDBDao
import com.example.movieapp.data.local.VeritabaniFavori
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    private var BASE_URL = "https://raw.githubusercontent.com/erik-sytnyk/movies-list/master/"


    @Singleton
    @Provides
    fun getRetrofitInstanceMoview(): APIService {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build().create()
    }


    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app, VeritabaniFavori::class.java, "your_db_name"
    ).allowMainThreadQueries().build() // The reason we can construct a database for the repo

    @Singleton
    @Provides
    fun provideYourDao(db: VeritabaniFavori) = db.yemeklerFavoriDao()
}