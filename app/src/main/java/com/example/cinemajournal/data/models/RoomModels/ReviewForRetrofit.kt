package com.example.cinemajournal.data.models.RoomModels

import androidx.room.ColumnInfo

data class ReviewForRetrofit(
    val user: User? = null,
    val movie: RoomMovieInfoForRetrofit? = null,
    var contentId: Int = 0,
    var rating: Double = 0.0,
    var notes: String? = null,
    var likes: List<Likes>? = null,
    var dislikes: List<Dislikes>? = null
)