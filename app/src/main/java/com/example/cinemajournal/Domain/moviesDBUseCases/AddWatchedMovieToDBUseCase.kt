package com.example.cinemajournal.Domain.moviesDBUseCases

import com.example.cinemajournal.data.MoviesDBRepository
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatchForRetrofit
import com.example.cinemajournal.data.models.RoomModels.WatchedMoviesForRetrofit
import javax.inject.Inject

class AddWatchedMovieToDBUseCase @Inject constructor(private val repository: MoviesDBRepository) {

    suspend operator fun invoke(requestBody: WatchedMoviesForRetrofit): WatchedMoviesForRetrofit? {
        return repository.addWatchedMovieToDB(requestBody)
    }
}