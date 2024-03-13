package com.example.cinemajournal.Domain.userUseCases

import com.example.cinemajournal.data.UsersRepository
import javax.inject.Inject

class DeleteUserByIdUseCase @Inject constructor(private val repository: UsersRepository) {

    operator fun invoke(id: Int)  {
        repository.deleteUserById(id)
    }
}