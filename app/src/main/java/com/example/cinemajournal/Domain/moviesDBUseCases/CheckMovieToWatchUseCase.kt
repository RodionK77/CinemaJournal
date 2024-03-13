package com.example.cinemajournal.Domain.moviesDBUseCases

import com.example.cinemajournal.data.MoviesDBRepository
import com.example.cinemajournal.data.models.RoomModels.User
import com.example.cinemajournal.data.models.RoomModels.UserForRetrofit
import javax.inject.Inject

class CheckMovieToWatchUseCase @Inject constructor(private val repository: MoviesDBRepository) {

    suspend operator fun invoke(movieId: Int, userId: Int): Boolean {
        return repository.checkMovieToWatch(movieId, userId)
    }
}