package com.example.cinemajournal.data.models.RoomModels

import androidx.room.ColumnInfo

data class WatchedMoviesForRetrofit(
    val user: User,
    val movie: RoomMovieInfo,
    var dateWatched: String? = null,
)