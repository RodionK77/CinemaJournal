package com.example.cinemajournal.data.models.RoomModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    primaryKeys = ["content_id","number"],
    foreignKeys = [
        ForeignKey(
            entity = RoomMovieInfo::class,
            parentColumns = ["id"],
            childColumns = ["content_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SeasonsInfo (
    /*@PrimaryKey(autoGenerate = true)
    var seasonsId: Int = 0,*/
    @ColumnInfo(name = "content_id") val contentId: Int,
    var number: Int = 0,
    var episodesCount: Int = 0,
)