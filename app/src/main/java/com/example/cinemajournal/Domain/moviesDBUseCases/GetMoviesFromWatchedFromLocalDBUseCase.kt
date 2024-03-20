package com.example.cinemajournal.Domain.moviesDBUseCases

import com.example.cinemajournal.data.MoviesDBRepository
import com.example.cinemajournal.data.models.RoomModels.RoomMovieInfo
import javax.inject.Inject

class GetMoviesFromWatchedFromLocalDBUseCase @Inject constructor(private val repository: MoviesDBRepository) {

    suspend operator fun invoke(): List<RoomMovieInfo>? {
        return repository.getMoviesFromWatchedFromLocalDB()
    }
}