package com.example.cinemajournal.Domain.moviesDBUseCases

import com.example.cinemajournal.data.MoviesDBRepository
import javax.inject.Inject

class DeleteWatchedMovieByIdFromLocalDBUseCase @Inject constructor(private val repository: MoviesDBRepository) {

    suspend operator fun invoke(userId: Int, movieId: Int) {
        repository.deleteWatchedMovieByIdFromLocalDB(userId, movieId)
    }
}