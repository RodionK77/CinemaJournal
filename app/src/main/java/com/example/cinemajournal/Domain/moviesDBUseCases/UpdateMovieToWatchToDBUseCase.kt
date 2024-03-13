package com.example.cinemajournal.Domain.moviesDBUseCases

import com.example.cinemajournal.data.MoviesDBRepository
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatchForRetrofit
import javax.inject.Inject

class UpdateMovieToWatchToDBUseCase @Inject constructor(private val repository: MoviesDBRepository) {

    suspend operator fun invoke(requestBody: MoviesToWatchForRetrofit) {
        repository.updateMovieToWatchToDB(requestBody)
    }
}