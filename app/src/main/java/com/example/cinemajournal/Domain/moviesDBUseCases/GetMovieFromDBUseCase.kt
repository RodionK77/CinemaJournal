package com.example.cinemajournal.Domain.moviesDBUseCases

import com.example.cinemajournal.data.MoviesDBRepository
import com.example.cinemajournal.data.models.RoomModels.RoomMovieInfoForRetrofit
import com.example.cinemajournal.data.models.RoomModels.UserForRetrofit
import javax.inject.Inject

class GetMovieFromDBUseCase @Inject constructor(private val repository: MoviesDBRepository) {

    suspend operator fun invoke(id: Int): RoomMovieInfoForRetrofit? {
        return repository.getMovie(id)
    }
}