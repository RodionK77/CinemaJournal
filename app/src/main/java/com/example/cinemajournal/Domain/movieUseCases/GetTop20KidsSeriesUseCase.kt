package com.example.cinemajournal.Domain.movieUseCases

import com.example.cinemajournal.data.MovieRepository
import com.example.example.MovieListResponse
import javax.inject.Inject

class GetTop20KidsSeriesUseCase @Inject constructor(private val repository: MovieRepository) {

    suspend operator fun invoke(): MovieListResponse?  {
        return repository.getTop20KidsSeries()
    }
}