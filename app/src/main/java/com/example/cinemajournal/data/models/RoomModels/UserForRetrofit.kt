package com.example.cinemajournal.data.models.RoomModels

import androidx.room.Entity
import androidx.room.PrimaryKey

data class UserForRetrofit(
    var id: Int,
    var username: String = "",
    var email: String = "",
    var role: Int = 0,
    var reviews: ArrayList<ReviewForRetrofit>? = arrayListOf(),
    var moviesToWatch: Set<MoviesToWatchForRetrofit>? = hashSetOf(),
    var watchedMovies: Set<WatchedMoviesForRetrofit>? = hashSetOf()
)