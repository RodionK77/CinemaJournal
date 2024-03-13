package com.example.cinemajournal.Domain.moviesDBUseCases

import com.example.cinemajournal.data.MoviesDBRepository
import com.example.cinemajournal.data.models.RoomModels.Genres
import com.example.cinemajournal.data.models.RoomModels.Persons
import javax.inject.Inject

class GetPersonsByIdFromLocalDBUseCase @Inject constructor(private val repository: MoviesDBRepository) {

    suspend operator fun invoke(movieId: Int): Persons? {
        return repository.getPersonsByIdFromLocalDB(movieId)
    }
}