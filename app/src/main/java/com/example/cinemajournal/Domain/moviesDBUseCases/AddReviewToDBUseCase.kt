package com.example.cinemajournal.Domain.moviesDBUseCases

import com.example.cinemajournal.data.MoviesDBRepository
import com.example.cinemajournal.data.models.RoomModels.Review
import com.example.cinemajournal.data.models.RoomModels.ReviewForRetrofit
import com.example.cinemajournal.data.models.RoomModels.WatchedMoviesForRetrofit
import javax.inject.Inject

class AddReviewToDBUseCase @Inject constructor(private val repository: MoviesDBRepository) {

    suspend operator fun invoke(requestBody: ReviewForRetrofit): ReviewForRetrofit? {
        return repository.addReviewToDB(requestBody)
    }
}