package com.example.cinemajournal.Domain.userUseCases

import com.example.cinemajournal.data.UsersRepository
import com.example.cinemajournal.data.models.RoomModels.WatchedMovies
import javax.inject.Inject

class SaveWatchedMovieToDatabaseUseCase @Inject constructor(private val repository: UsersRepository) {

    operator fun invoke(item: WatchedMovies)  {
        repository.saveWatchedMovieToDatabase(item)
    }
}