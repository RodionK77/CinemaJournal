package com.example.cinemajournal.Domain.userUseCases

import com.example.cinemajournal.data.UsersRepository
import com.example.cinemajournal.data.models.AuthResponse
import javax.inject.Inject

class SignOutUserUseCase @Inject constructor(private val repository: UsersRepository) {

    suspend operator fun invoke(): AuthResponse {
        return repository.signOutUser()
    }
}