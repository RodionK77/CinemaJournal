package com.example.cinemajournal.Domain.moviesDBUseCases

import com.example.cinemajournal.data.MovieRepository
import com.example.cinemajournal.data.MoviesDBRepository
import com.example.cinemajournal.data.models.RoomModels.RoomMovieInfo
import com.example.example.MovieInfo
import javax.inject.Inject

class AddMovieToDBUseCase @Inject constructor(private val repository: MoviesDBRepository) {

    suspend operator fun invoke(requestBody: RoomMovieInfo): RoomMovieInfo? {
        return repository.addMovieToDB(requestBody)
    }
}