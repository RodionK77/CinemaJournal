package com.example.cinemajournal.Domain.userUseCases

import com.example.cinemajournal.data.UsersRepository
import javax.inject.Inject

class DeleteMovieToWatchByIdUseCase @Inject constructor(private val repository: UsersRepository) {

    operator fun invoke(id: Long)  {
        repository.deleteMovieToWatchById(id)
    }
}