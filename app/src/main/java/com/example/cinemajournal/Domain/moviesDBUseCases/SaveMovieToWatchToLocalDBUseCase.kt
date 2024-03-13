package com.example.cinemajournal.Domain.moviesDBUseCases

import com.example.cinemajournal.data.MoviesDBRepository
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatch
import com.example.cinemajournal.data.models.RoomModels.Review
import javax.inject.Inject

class SaveMovieToWatchToLocalDBUseCase @Inject constructor(private val repository: MoviesDBRepository) {

    suspend operator fun invoke(requestBody: MoviesToWatch) {
        repository.saveMovieToWatchToLocalDB(requestBody)
    }
}