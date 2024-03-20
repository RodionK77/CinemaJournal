package com.example.cinemajournal.Domain.moviesDBUseCases

import com.example.cinemajournal.data.MoviesDBRepository
import com.example.cinemajournal.data.models.RoomModels.DislikesForRetrofit
import com.example.cinemajournal.data.models.RoomModels.LikesForRetrofit
import javax.inject.Inject

class GetDislikesByIdFromLocalDBUseCase @Inject constructor(private val repository: MoviesDBRepository) {

    suspend operator fun invoke(movieId: Int): List<DislikesForRetrofit>{
        return repository.getDislikesByIdFromLocalDB(movieId)
    }
}