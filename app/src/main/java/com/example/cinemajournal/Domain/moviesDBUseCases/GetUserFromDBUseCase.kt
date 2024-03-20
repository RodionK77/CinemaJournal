package com.example.cinemajournal.Domain.moviesDBUseCases

import com.example.cinemajournal.data.MoviesDBRepository
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatchForRetrofit
import com.example.cinemajournal.data.models.RoomModels.User
import com.example.cinemajournal.data.models.RoomModels.UserForRetrofit
import javax.inject.Inject

class GetUserFromDBUseCase @Inject constructor(private val repository: MoviesDBRepository) {

    suspend operator fun invoke(id: Int): UserForRetrofit? {
        return repository.getUser(id)
    }
}