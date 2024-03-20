package com.example.cinemajournal.data.models.RoomModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    primaryKeys = ["content_id","name"],
    foreignKeys = [
        ForeignKey(
            entity = RoomMovieInfo::class,
            parentColumns = ["id"],
            childColumns = ["content_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Genres (
    /*@PrimaryKey(autoGenerate = true)
    var genresId: Int = 0,*/
    @ColumnInfo(name = "content_id") val contentId: Int,
    /*@PrimaryKey*/ var name: String,
)