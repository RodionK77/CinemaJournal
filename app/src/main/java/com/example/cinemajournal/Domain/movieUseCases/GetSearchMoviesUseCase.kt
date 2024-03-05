package com.example.cinemajournal.Domain.movieUseCases

import com.example.cinemajournal.data.MovieRepository
import com.example.example.MovieListResponse
import javax.inject.Inject

class GetSearchMoviesUseCase @Inject constructor(private val repository: MovieRepository) {

    suspend operator fun invoke(query: String): MovieListResponse?  {
        return repository.getSearchMovies(query = query)
    }
}