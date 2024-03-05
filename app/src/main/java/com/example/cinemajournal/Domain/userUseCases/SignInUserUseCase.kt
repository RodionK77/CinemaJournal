package com.example.cinemajournal.Domain.userUseCases

import com.example.cinemajournal.data.UsersRepository
import com.example.cinemajournal.data.models.AuthResponse
import com.example.cinemajournal.data.models.SignInRequest
import com.example.cinemajournal.data.models.SignUpRequest
import javax.inject.Inject

class SignInUserUseCase @Inject constructor(private val repository: UsersRepository) {

    suspend operator fun invoke(request: SignInRequest): AuthResponse {
        return repository.signInUser(request)
    }
}