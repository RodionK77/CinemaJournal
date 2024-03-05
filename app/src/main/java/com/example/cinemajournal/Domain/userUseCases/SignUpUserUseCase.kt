package com.example.cinemajournal.Domain.userUseCases

import com.example.cinemajournal.data.UsersRepository
import com.example.cinemajournal.data.models.SignUpRequest
import com.example.cinemajournal.data.models.AuthResponse
import javax.inject.Inject

class SignUpUserUseCase @Inject constructor(private val repository: UsersRepository) {

    suspend operator fun invoke(request: SignUpRequest): AuthResponse  {
        return repository.signUpUser(request)
    }
}