package com.example.cinemajournal.data.models.RoomModels

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class SeasonsInfoForRetrofit (
    //var seasonsId: Int,
    val contentId: Int?,
    val movieId: Int? = null,
    var number: Int = 0,
    var episodesCount: Int = 0,
)