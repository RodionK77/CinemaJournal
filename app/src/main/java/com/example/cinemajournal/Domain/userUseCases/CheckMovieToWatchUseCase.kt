package com.example.cinemajournal.Domain.userUseCases

import com.example.cinemajournal.data.UsersRepository
import javax.inject.Inject

class CheckMovieToWatchUseCase @Inject constructor(private val repository: UsersRepository) {

    operator fun invoke(movieId: Long, userId: String): Boolean  {
        return repository.checkMovieToWatch(movieId, userId)
    }
}