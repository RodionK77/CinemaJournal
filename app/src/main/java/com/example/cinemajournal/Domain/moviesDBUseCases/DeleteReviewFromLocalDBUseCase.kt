package com.example.cinemajournal.Domain.moviesDBUseCases

import com.example.cinemajournal.data.MoviesDBRepository
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatchForRetrofit
import com.example.cinemajournal.data.models.RoomModels.Review
import javax.inject.Inject

class DeleteReviewFromLocalDBUseCase @Inject constructor(private val repository: MoviesDBRepository) {

    suspend operator fun invoke(requestBody: Review) {
        return repository.deleteReviewFromLocalDB(requestBody)
    }
}