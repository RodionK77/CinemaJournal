package com.example.cinemajournal.Domain.moviesDBUseCases

import com.example.cinemajournal.data.MoviesDBRepository
import com.example.cinemajournal.data.models.RoomModels.Persons
import com.example.cinemajournal.data.models.RoomModels.Review
import javax.inject.Inject

class SavePersonsToLocalDBUseCase @Inject constructor(private val repository: MoviesDBRepository) {

    suspend operator fun invoke(requestBody: Persons) {
        repository.savePersonsToLocalDB(requestBody)
    }
}