package com.example.cinemajournal.Domain.moviesDBUseCases

import com.example.cinemajournal.data.MoviesDBRepository
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatch
import com.example.cinemajournal.data.models.RoomModels.User
import com.example.cinemajournal.data.models.RoomModels.WatchedMovies
import javax.inject.Inject

class GetAllWatchedMoviesByIdFromLocalDBUseCase @Inject constructor(private val repository: MoviesDBRepository) {

    suspend operator fun invoke(requestBody: User): List<WatchedMovies>? {
        return repository.getAllWatchedMoviesByIdFromLocalDB(requestBody)
    }
}