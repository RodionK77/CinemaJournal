package com.example.cinemajournal.data.models.RoomModels

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class CountriesForRetrofit (
    var countriesId: Int?,
    val contentId: Int?,
    var name: String? = null,
    val movieId: Int? = null
)