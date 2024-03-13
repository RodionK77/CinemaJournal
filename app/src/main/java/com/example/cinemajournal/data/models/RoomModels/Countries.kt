package com.example.cinemajournal.data.models.RoomModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = RoomMovieInfo::class,
            parentColumns = ["id"],
            childColumns = ["content_id"]
        )
    ]
)
data class Countries (
    @PrimaryKey var countriesId: Int,
    @ColumnInfo(name = "content_id") val contentId: Int,
    var name: String? = null,
)