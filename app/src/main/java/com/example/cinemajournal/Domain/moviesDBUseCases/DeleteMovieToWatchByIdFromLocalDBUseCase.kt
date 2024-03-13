package com.example.cinemajournal.Domain.moviesDBUseCases

import com.example.cinemajournal.data.MoviesDBRepository
import javax.inject.Inject

class DeleteMovieToWatchByIdFromLocalDBUseCase @Inject constructor(private val repository: MoviesDBRepository) {

    suspend operator fun invoke(movieId: Int, userId: Int) {
        repository.deleteMovieToWatchByIdFromLocalDB(movieId, userId)
    }
}