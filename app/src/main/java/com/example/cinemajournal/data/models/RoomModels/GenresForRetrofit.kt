package com.example.cinemajournal.data.models.RoomModels

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class GenresForRetrofit (
    var genresId: Int?,
    var contentId: Int?,
    var name: String? = null,
    var movieId: Int? = null
)