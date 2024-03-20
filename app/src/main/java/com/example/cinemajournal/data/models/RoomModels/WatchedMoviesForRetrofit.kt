package com.example.cinemajournal.data.models.RoomModels

import androidx.room.ColumnInfo

data class WatchedMoviesForRetrofit(
    val user: User?,
    val movie: RoomMovieInfoForRetrofit?,
    var dateWatched: String? = null,
    val contentId: Int? = null
)