package com.example.cinemajournal.data.API

import com.example.cinemajournal.data.models.AuthResponse
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatch
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatchForRetrofit
import com.example.cinemajournal.data.models.RoomModels.Review
import com.example.cinemajournal.data.models.RoomModels.RoomMovieInfo
import com.example.cinemajournal.data.models.RoomModels.User
import com.example.cinemajournal.data.models.RoomModels.UserForRetrofit
import com.example.cinemajournal.data.models.RoomModels.WatchedMovies
import com.example.cinemajournal.data.models.RoomModels.WatchedMoviesForRetrofit
import com.example.cinemajournal.data.models.SignUpRequest
import okhttp3.internal.http.hasBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT

interface MoviesDBApi {

    @GET("getUser")
    suspend fun getUser(@Body requestBody: User): UserForRetrofit?

    @POST("addMovie")
    suspend fun addMovieToDB(@Body requestBody: RoomMovieInfo): RoomMovieInfo

    @POST("addMovieToWatch")
    suspend fun addMovieToWatchToDB(@Body requestBody: MoviesToWatchForRetrofit): MoviesToWatchForRetrofit

    @HTTP(method = "DELETE", path = "deleteMovietoWatch", hasBody = true)
    suspend fun deleteMovieToWatchFromDB(@Body requestBody: MoviesToWatchForRetrofit)

    @PUT("updateMovieToWatch")
    suspend fun updateMovieToWatchToDB(@Body requestBody: MoviesToWatchForRetrofit)

    @POST("addWatchedMovie")
    suspend fun addWatchedMovieToDB(@Body requestBody: WatchedMoviesForRetrofit): WatchedMoviesForRetrofit?

    @DELETE("deleteWatchedMovie")
    suspend fun deleteWatchedMovieFromDB(@Body requestBody: WatchedMoviesForRetrofit)

    @PUT("updateWatchedMovie")
    suspend fun updateWatchedMovieToDB(@Body requestBody: WatchedMoviesForRetrofit)

    @POST("addReview")
    suspend fun addReviewToDB(@Body requestBody: Review): Review

    @PUT("updateReview")
    suspend fun updateReviewToDB(@Body requestBody: Review)
}