package com.example.cinemajournal.Domain.moviesDBUseCases

import com.example.cinemajournal.data.MoviesDBRepository
import com.example.cinemajournal.data.models.RoomModels.Likes
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatch
import com.example.cinemajournal.data.models.RoomModels.User
import javax.inject.Inject

class GetAllLikesByIdFromLocalDBUseCase @Inject constructor(private val repository: MoviesDBRepository) {

    suspend operator fun invoke(requestBody: User): List<Likes>? {
        return repository.getAllLikesByIdFromLocalDB(requestBody)
    }
}