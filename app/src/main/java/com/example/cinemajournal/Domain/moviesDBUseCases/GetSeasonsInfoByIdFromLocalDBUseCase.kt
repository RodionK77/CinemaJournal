package com.example.cinemajournal.Domain.moviesDBUseCases

import com.example.cinemajournal.data.MoviesDBRepository
import com.example.cinemajournal.data.models.RoomModels.Persons
import com.example.cinemajournal.data.models.RoomModels.SeasonsInfo
import javax.inject.Inject

class GetSeasonsInfoByIdFromLocalDBUseCase @Inject constructor(private val repository: MoviesDBRepository) {

    suspend operator fun invoke(movieId: Int): SeasonsInfo? {
        return repository.getSeasonsInfoByIdFromLocalDB(movieId)
    }
}