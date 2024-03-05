package com.example.cinemajournal.Domain.movieUseCases

import com.example.cinemajournal.data.MovieRepository
import com.example.example.MovieListResponse
import javax.inject.Inject

class GetFilteredMoviesUseCase @Inject constructor(private val repository: MovieRepository) {

    suspend operator fun invoke(type: List<String>?, year:String?, rating: String?, ageRating: String?, time: String?, genresName: List<String>?, countriesName: List<String>?): MovieListResponse?  {
        return repository.getFilteredMovies(type = type, year = year, rating = rating, ageRating = ageRating, time = time, genresName = genresName, countriesName = countriesName)
    }
}