package com.example.cinemajournal.data.models

import com.example.example.MovieInfo

data class ReminderModel(
    var reminderDate: String? = null,
    var reminderHour: Int? = null,
    var reminderMinute: Int? = null
)