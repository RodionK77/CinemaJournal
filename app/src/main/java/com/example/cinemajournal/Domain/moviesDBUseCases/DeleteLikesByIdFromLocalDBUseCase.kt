package com.example.cinemajournal.Domain.moviesDBUseCases

import com.example.cinemajournal.data.MoviesDBRepository
import com.example.cinemajournal.data.models.RoomModels.Likes
import javax.inject.Inject

class DeleteLikesByIdFromLocalDBUseCase @Inject constructor(private val repository: MoviesDBRepository) {

    suspend operator fun invoke(item: Likes) {
        repository.deleteLikesByIdFromLocalDB(item)
    }
}