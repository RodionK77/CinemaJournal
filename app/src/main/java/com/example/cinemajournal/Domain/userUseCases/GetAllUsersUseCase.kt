package com.example.cinemajournal.Domain.userUseCases

import com.example.cinemajournal.data.UsersRepository
import com.example.cinemajournal.data.models.RoomModels.User
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(private val repository: UsersRepository) {

    suspend operator fun invoke(): List<User>  {
        return repository.getAllUsers()
    }
}