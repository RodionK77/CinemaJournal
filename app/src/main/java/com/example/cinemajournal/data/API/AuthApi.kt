package com.example.cinemajournal.data.API

import com.example.cinemajournal.data.models.SignInRequest
import com.example.cinemajournal.data.models.SignUpRequest
import com.example.cinemajournal.data.models.AuthResponse
import com.example.example.MovieListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {

    @POST("auth/signup")
    suspend fun signUpUser(@Body requestBody: SignUpRequest): AuthResponse

    @POST("auth/signin")
    suspend fun signInUser(@Body requestBody: SignInRequest): AuthResponse

    @POST("auth/signout")
    suspend fun signOutUser(): AuthResponse

}

