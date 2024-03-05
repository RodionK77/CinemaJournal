package com.example.cinemajournal.Domain.userUseCases

import com.example.cinemajournal.data.UsersRepository
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatch
import javax.inject.Inject

class SaveMovieToWatchToDatabaseUseCase @Inject constructor(private val repository: UsersRepository) {

    operator fun invoke(item: MoviesToWatch)  {
        repository.saveMovieToWatchToDatabase(item)
    }
}