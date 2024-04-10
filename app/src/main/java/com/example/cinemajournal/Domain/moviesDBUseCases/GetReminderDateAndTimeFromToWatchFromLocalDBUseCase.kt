package com.example.cinemajournal.Domain.moviesDBUseCases

import android.util.Log
import com.example.cinemajournal.data.MoviesDBRepository
import com.example.cinemajournal.data.models.ReminderModel
import com.example.cinemajournal.data.models.RoomModels.RoomMovieInfo
import javax.inject.Inject

class GetReminderDateAndTimeFromToWatchFromLocalDBUseCase @Inject constructor(private val repository: MoviesDBRepository) {

    suspend operator fun invoke(movieId: Int): ReminderModel {
        return ReminderModel(
            reminderDate = repository.getReminderDateByIdFromToWatchFromLocalDB(movieId),
            reminderHour = repository.getReminderHourByIdFromToWatchFromLocalDB(movieId),
            reminderMinute = repository.getReminderMinuteByIdFromToWatchFromLocalDB(movieId)
        )
    }
}