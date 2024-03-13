package com.example.cinemajournal.Domain.moviesDBUseCases

import com.example.cinemajournal.data.MoviesDBRepository
import com.example.cinemajournal.data.models.RoomModels.WatchedMoviesForRetrofit
import javax.inject.Inject

class UpdateWatchedMovieToDBUseCase @Inject constructor(private val repository: MoviesDBRepository) {

    suspend operator fun invoke(requestBody: WatchedMoviesForRetrofit) {
        repository.updateWatchedMovieToDB(requestBody)
    }
}