package com.example.cinemajournal.Domain.moviesDBUseCases

import com.example.cinemajournal.data.MoviesDBRepository
import com.example.cinemajournal.data.models.RoomModels.Countries
import com.example.cinemajournal.data.models.RoomModels.Dislikes
import javax.inject.Inject

class SaveCountriesToLocalDBUseCase @Inject constructor(private val repository: MoviesDBRepository) {

    suspend operator fun invoke(requestBody: Countries) {
        repository.saveCountriesToLocalDB(requestBody)
    }
}