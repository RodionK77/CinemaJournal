package com.example.cinemajournal.Domain.moviesDBUseCases

import com.example.cinemajournal.data.MoviesDBRepository
import com.example.cinemajournal.data.models.RoomModels.Countries
import com.example.cinemajournal.data.models.RoomModels.User
import com.example.cinemajournal.data.models.RoomModels.WatchedMovies
import javax.inject.Inject

class GetCountriesByIdFromLocalDBUseCase @Inject constructor(private val repository: MoviesDBRepository) {

    suspend operator fun invoke(movieId: Int): Countries? {
        return repository.getCountriesByIdFromLocalDB(movieId)
    }
}