package com.example.cinemajournal.Domain.movieUseCases

import com.example.cinemajournal.data.MovieRepository
import javax.inject.Inject

class RefreshSearchMoviesUseCase @Inject constructor(private val repository: MovieRepository) {

    operator fun invoke(query: String)  {
        repository.refreshSearchMovies(query)
    }
}