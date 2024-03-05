package com.example.cinemajournal.data.models.RoomModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
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
data class Reviews(
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "movie_id") val movieId: Long,
    var userRating: Double,
    var likes: String,
    var dislikes: String,
    var notes: String
)