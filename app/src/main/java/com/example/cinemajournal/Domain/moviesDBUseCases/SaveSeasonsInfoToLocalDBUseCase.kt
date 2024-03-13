package com.example.cinemajournal.Domain.moviesDBUseCases

import com.example.cinemajournal.data.MoviesDBRepository
import com.example.cinemajournal.data.models.RoomModels.Review
import com.example.cinemajournal.data.models.RoomModels.SeasonsInfo
import javax.inject.Inject

class SaveSeasonsInfoToLocalDBUseCase @Inject constructor(private val repository: MoviesDBRepository) {

    suspend operator fun invoke(requestBody: SeasonsInfo) {
        repository.saveSeasonsInfoToLocalDB(requestBody)
    }
}