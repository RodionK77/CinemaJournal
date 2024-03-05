package com.example.cinemajournal.Domain.userUseCases

import com.example.cinemajournal.data.UsersRepository
import javax.inject.Inject

class DeleteWatchedMovieByIdUseCase @Inject constructor(private val repository: UsersRepository) {

    operator fun invoke(id: Long)  {
        repository.deleteWatchedMovieById(id)
    }
}