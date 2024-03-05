package com.example.cinemajournal.data.di

import com.example.cinemajournal.data.API.AuthApi
import com.example.cinemajournal.data.API.MoviesApi
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    @Qualifiers.Movies
    fun provideMoviesRetrofit() : Retrofit = Retrofit.Builder()
        .baseUrl("https://api.kinopoisk.dev")
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()

    @Provides
    @Singleton
    @Qualifiers.Auth
    fun provideAuthRetrofit() : Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080/api/")
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()

    @Provides
    @Singleton
    fun provideRetrofitMoviesService(@Qualifiers.Movies retrofit : Retrofit) : MoviesApi = retrofit.create(MoviesApi::class.java)

    @Provides
    @Singleton
    fun provideRetrofitAuthService(@Qualifiers.Auth retrofit : Retrofit) : AuthApi = retrofit.create(AuthApi::class.java)

    /*@Provides
    @Singleton
    fun provideMainRemoteData(mainService : MainService) : MainRemoteData = MainRemoteData(mainService)*/
}