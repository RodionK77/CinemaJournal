package com.example.cinemajournal.Domain.moviesDBUseCases

import com.example.cinemajournal.data.MoviesDBRepository
import com.example.cinemajournal.data.models.RoomModels.Genres
import com.example.cinemajournal.data.models.RoomModels.Likes
import javax.inject.Inject

class SaveGenresToLocalDBUseCase @Inject constructor(private val repository: MoviesDBRepository) {

    suspend operator fun invoke(requestBody: Genres) {
        repository.saveGenresToLocalDB(requestBody)
    }
}