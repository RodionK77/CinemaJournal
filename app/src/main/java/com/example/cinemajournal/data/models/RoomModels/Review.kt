package com.example.cinemajournal.data.models.RoomModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    primaryKeys = ["user_id","movie_id"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"]
        ),
        ForeignKey(
            entity = RoomMovieInfo::class,
            parentColumns = ["id"],
            childColumns = ["movie_id"]
        )
    ]
)
data class Review(
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "movie_id") val movieId: Int,
    var contentId: Int = 0,
    var rating: Double = 0.0,
    var notes: String? = null
)