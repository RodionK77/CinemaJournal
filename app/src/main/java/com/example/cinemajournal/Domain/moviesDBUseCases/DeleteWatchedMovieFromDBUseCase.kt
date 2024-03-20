package com.example.cinemajournal.Domain.moviesDBUseCases

import com.example.cinemajournal.data.MoviesDBRepository
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatchForRetrofit
import com.example.cinemajournal.data.models.RoomModels.RoomMovieInfoForRetrofit
import com.example.cinemajournal.data.models.RoomModels.WatchedMoviesForRetrofit
import retrofit2.Response
import javax.inject.Inject

class DeleteWatchedMovieFromDBUseCase @Inject constructor(private val repository: MoviesDBRepository) {

    suspend operator fun invoke(requestBody: WatchedMoviesForRetrofit): Response<Unit> {
        return repository.deleteWatchedMovieFromDB(requestBody)
    }
}