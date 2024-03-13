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
data class SeasonsInfo (
    @PrimaryKey var seasonsId: Int,
    @ColumnInfo(name = "content_id") val contentId: Int,
    var number: Int = 0,
    var episodesCount: Int = 0,
)