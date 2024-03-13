package com.example.cinemajournal.Domain.moviesDBUseCases

import com.example.cinemajournal.data.MoviesDBRepository
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatch
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatchForRetrofit
import com.example.cinemajournal.data.models.RoomModels.RoomMovieInfo
import javax.inject.Inject

class DeleteMovieToWatchFromDBUseCase @Inject constructor(private val repository: MoviesDBRepository) {

    suspend operator fun invoke(requestBody: MoviesToWatchForRetrofit) {
        return repository.deleteMovieToWatchFromDB(requestBody)
    }
}