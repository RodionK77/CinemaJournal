package com.example.cinemajournal.data.API

import com.example.cinemajournal.data.models.AuthResponse
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatch
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatchForRetrofit
import com.example.cinemajournal.data.models.RoomModels.Review
import com.example.cinemajournal.data.models.RoomModels.ReviewForRetrofit
import com.example.cinemajournal.data.models.RoomModels.RoomMovieInfo
import com.example.cinemajournal.data.models.RoomModels.RoomMovieInfoForRetrofit
import com.example.cinemajournal.data.models.RoomModels.User
import com.example.cinemajournal.data.models.RoomModels.UserForRetrofit
import com.example.cinemajournal.data.models.RoomModels.WatchedMovies
import com.example.cinemajournal.data.models.RoomModels.WatchedMoviesForRetrofit
import com.example.cinemajournal.data.models.SignUpRequest
import okhttp3.internal.http.hasBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MoviesDBApi {

    @GET("getUser/{id}")
    suspend fun getUser(@Path("id") id: Int): UserForRetrofit?

    @GET("getMovie/{id}")
    suspend fun getMovie(@Path("id") id: Int): RoomMovieInfoForRetrofit?

    @GET("getMoviesByUser/{id}")
    suspend fun getAllMoviesByUserId(@Path("id") id: Int): List<RoomMovieInfoForRetrofit>?

    @POST("addMovie")
    suspend fun addMovieToDB(@Body requestBody: RoomMovieInfoForRetrofit): RoomMovieInfoForRetrofit

    @POST("addMovieToWatch")
    suspend fun addMovieToWatchToDB(@Body requestBody: MoviesToWatchForRetrofit): MoviesToWatchForRetrofit

    @HTTP(method = "DELETE", path = "deleteMovietoWatch", hasBody = true)
    suspend fun deleteMovieToWatchFromDB(@Body requestBody: MoviesToWatchForRetrofit): Response<Unit>

    @PUT("updateMovieToWatch")
    suspend fun updateMovieToWatchToDB(@Body requestBody: MoviesToWatchForRetrofit)

    @POST("addWatchedMovie")
    suspend fun addWatchedMovieToDB(@Body requestBody: WatchedMoviesForRetrofit): WatchedMoviesForRetrofit?

    @HTTP(method = "DELETE", path = "deleteWatchedMovie", hasBody = true)
    suspend fun deleteWatchedMovieFromDB(@Body requestBody: WatchedMoviesForRetrofit): Response<Unit>

    @PUT("updateWatchedMovie")
    suspend fun updateWatchedMovieToDB(@Body requestBody: WatchedMoviesForRetrofit)

    @POST("addReview")
    suspend fun addReviewToDB(@Body requestBody: ReviewForRetrofit): ReviewForRetrofit?

    @PUT("updateReview")
    suspend fun updateReviewToDB(@Body requestBody: Review)
}