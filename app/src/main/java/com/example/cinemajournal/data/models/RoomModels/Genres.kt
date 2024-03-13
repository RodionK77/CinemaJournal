package com.example.cinemajournal.data.models.RoomModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = RoomMovieInfo::class,
            parentColumns = ["id"],
            childColumns = ["content_id"]
        )
    ]
)
data class Genres (
    @PrimaryKey var genresId: Int,
    @ColumnInfo(name = "content_id") val contentId: Int,
    var name: String? = null,
)