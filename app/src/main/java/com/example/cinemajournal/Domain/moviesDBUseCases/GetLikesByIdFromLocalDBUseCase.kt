package com.example.cinemajournal.Domain.moviesDBUseCases

import com.example.cinemajournal.data.MoviesDBRepository
import com.example.cinemajournal.data.models.RoomModels.LikesForRetrofit
import javax.inject.Inject

class GetLikesByIdFromLocalDBUseCase @Inject constructor(private val repository: MoviesDBRepository) {

    suspend operator fun invoke(movieId: Int): List<LikesForRetrofit>{
        return repository.getLikesByIdFromLocalDB(movieId)
    }
}