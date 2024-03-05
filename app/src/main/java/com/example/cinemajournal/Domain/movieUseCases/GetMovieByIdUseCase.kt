package com.example.cinemajournal.Domain.movieUseCases

import com.example.cinemajournal.data.MovieRepository
import com.example.example.MovieInfo
import com.example.example.MovieListResponse
import javax.inject.Inject

class GetMovieByIdUseCase @Inject constructor(private val repository: MovieRepository) {

    suspend operator fun invoke(id: Int): MovieInfo? {
        return repository.getMovieById(id)
    }
}