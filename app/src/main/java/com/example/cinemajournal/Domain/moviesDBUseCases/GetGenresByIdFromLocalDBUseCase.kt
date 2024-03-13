package com.example.cinemajournal.Domain.moviesDBUseCases

import com.example.cinemajournal.data.MoviesDBRepository
import com.example.cinemajournal.data.models.RoomModels.Countries
import com.example.cinemajournal.data.models.RoomModels.Genres
import javax.inject.Inject

class GetGenresByIdFromLocalDBUseCase @Inject constructor(private val repository: MoviesDBRepository) {

    suspend operator fun invoke(movieId: Int): Genres? {
        return repository.getGenresByIdFromLocalDB(movieId)
    }
}