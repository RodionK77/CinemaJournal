package com.example.cinemajournal.data.models.RoomModels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomMovieInfo (
    @PrimaryKey var id: Long,
    var name: String
)