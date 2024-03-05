package com.example.cinemajournal.Domain.movieUseCases

import com.example.cinemajournal.data.MovieRepository
import javax.inject.Inject


class RefreshMoviesUseCase @Inject constructor(private val repository: MovieRepository) {

    operator fun invoke()  {
        //repository.refreshMovies()
    }
}