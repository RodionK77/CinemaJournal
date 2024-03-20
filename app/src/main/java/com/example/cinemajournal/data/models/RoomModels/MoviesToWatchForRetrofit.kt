package com.example.cinemajournal.data.models.RoomModels

import androidx.room.ColumnInfo

data class MoviesToWatchForRetrofit(
    val user: User?,
    val movie: RoomMovieInfoForRetrofit?,
    var contentId: Int? = null,
    var reminderDate: String? = null,
)