package com.example.cinemajournal.data.models.RoomModels

import androidx.room.ColumnInfo

data class ReviewForRetrofit(
    val user: User,
    val movie: RoomMovieInfo,
    var contentId: Int = 0,
    var rating: Double = 0.0,
    var notes: String? = null,
    var likes: Set<Likes>? = null,
    var dislikes: Set<Dislikes>? = null
)