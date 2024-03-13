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
data class Persons (
    @PrimaryKey var personsId: Int,
    @ColumnInfo(name = "content_id") val contentId: Int,
    var photo: String? = null,
    var name: String? = null,
    var enName: String? = null,
    var description: String? = null,
    var profession: String? = null,
    var enProfession: String? = null
)