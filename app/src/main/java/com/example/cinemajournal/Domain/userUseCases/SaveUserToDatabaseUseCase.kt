package com.example.cinemajournal.Domain.userUseCases

import com.example.cinemajournal.data.UsersRepository
import com.example.cinemajournal.data.models.RoomModels.User
import javax.inject.Inject

class SaveUserToDatabaseUseCase @Inject constructor(private val repository: UsersRepository) {

    operator fun invoke(user: User)  {
        repository.saveUserToDatabase(user)
    }
}