package com.example.cinemajournal.data.models.RoomModels

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cinemajournal.data.models.Review
import com.example.example.MovieInfo

@Entity
data class User(
    @PrimaryKey var id: String,
    var username: String = "",
    var email: String,
    var role: Int = 0,
    //var watchedMovies: ArrayList<Review> = arrayListOf(),
    //var moviesToWatch: ArrayList<MovieInfo> = arrayListOf()
)
