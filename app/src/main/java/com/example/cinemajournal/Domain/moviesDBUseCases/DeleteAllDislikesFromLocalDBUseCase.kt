package com.example.cinemajournal.Domain.moviesDBUseCases

import com.example.cinemajournal.data.MoviesDBRepository
import javax.inject.Inject

class DeleteAllDislikesFromLocalDBUseCase @Inject constructor(private val repository: MoviesDBRepository) {

    suspend operator fun invoke() {
        repository.deleteAllDislikesFromLocalDB()
    }
}