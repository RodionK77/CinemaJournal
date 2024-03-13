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
data class Likes (
    @PrimaryKey var likesId: Int,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "movie_id") val movieId: Int,
    var description: String? = null,
)