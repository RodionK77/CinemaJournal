package com.example.cinemajournal.data.models.RoomModels

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class LikesForRetrofit (
    var likesId: Int?,
    val userId: Int?,
    val movieId: Int?,
    var description: String? = null,
    val contentId: Int? = null
)