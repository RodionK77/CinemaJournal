package com.example.cinemajournal.ui.theme.screens.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemajournal.Domain.moviesDBUseCases.AddReviewToDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetUserFromDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveCountriesToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveDislikesToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveGenresToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveLikesToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveMovieToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveMovieToWatchToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SavePersonsToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveReviewToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveSeasonsInfoToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveWatchedMovieToLocalDBUseCase
import com.example.cinemajournal.data.models.RoomModels.Countries
import com.example.cinemajournal.data.models.RoomModels.Dislikes
import com.example.cinemajournal.data.models.RoomModels.Likes
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatch
import com.example.cinemajournal.data.models.RoomModels.Review
import com.example.cinemajournal.data.models.RoomModels.RoomMovieInfo
import com.example.cinemajournal.data.models.RoomModels.User
import com.example.cinemajournal.data.models.RoomModels.UserForRetrofit
import com.example.cinemajournal.data.models.RoomModels.WatchedMovies
import kotlinx.coroutines.launch
import javax.inject.Inject

data class JournalsUiState(
    val user: UserForRetrofit? = null
)


class JournalsViewModel @Inject constructor (val getUserFromDBUseCase: GetUserFromDBUseCase,
                                             val saveReviewToLocalDBUseCase: SaveReviewToLocalDBUseCase,
                                             val saveLikesToLocalDBUseCase: SaveLikesToLocalDBUseCase,
                                             val saveDislikesToLocalDBUseCase: SaveDislikesToLocalDBUseCase,
                                             val saveMovieToWatchToLocalDBUseCase: SaveMovieToWatchToLocalDBUseCase,
                                             val saveWatchedMoviesToLocalDBUseCase: SaveWatchedMovieToLocalDBUseCase,
                                             val saveMovieToLocalDBUseCase: SaveMovieToLocalDBUseCase,
                                             val saveCountriesToLocalDBUseCase: SaveCountriesToLocalDBUseCase,
                                             val saveGenresToLocalDBUseCase: SaveGenresToLocalDBUseCase,
                                             val savePersonsToLocalDBUseCase: SavePersonsToLocalDBUseCase,
                                             val saveSeasonsInfoToLocalDBUseCase: SaveSeasonsInfoToLocalDBUseCase) : ViewModel() {

    var uiState by mutableStateOf(JournalsUiState())
        private set

    fun getUserFromDB(user: User){
        viewModelScope.launch {
            kotlin.runCatching { getUserFromDBUseCase(user) }
                .onSuccess { response ->
                    uiState = uiState.copy(user = response)
                }
                .onFailure { Log.d("R", "Проблема с получением данных пользователя", ) }
        }
    }

    fun updateLocalDB(user: UserForRetrofit){
        user.reviews?.forEach {

            viewModelScope.launch {
                kotlin.runCatching { saveReviewToLocalDBUseCase(
                    Review(userId = user.id, movieId = it.contentId, rating = it.rating, notes = it.notes)
                ) }
                    .onSuccess { Log.d("R", "Ревью загрузились", ) }
                    .onFailure { Log.d("R", "Ревью не загрузились: ${it.message}", ) }
            }

            it.dislikes?.forEach {

                viewModelScope.launch {
                    kotlin.runCatching { saveDislikesToLocalDBUseCase(
                        Dislikes(dislikesId = it.dislikesId, userId = user.id, movieId = it.movieId, description = it.description)
                    ) }
                        .onSuccess { Log.d("R", "Дизлайки загрузились", ) }
                        .onFailure { Log.d("R", "Дизлайки не загрузились: ${it.message}", ) }
                }

            }
            it.likes?.forEach {

                viewModelScope.launch {
                    kotlin.runCatching { saveLikesToLocalDBUseCase(
                        Likes(likesId = it.likesId, userId = user.id, movieId = it.movieId, description = it.description)
                    ) }
                        .onSuccess { Log.d("R", "Лайки загрузились", ) }
                        .onFailure { Log.d("R", "Лайки не загрузились: ${it.message}", ) }
                }

            }
        }
        user.moviesToWatch?.forEach {

            viewModelScope.launch {
                kotlin.runCatching { saveMovieToWatchToLocalDBUseCase(
                    MoviesToWatch(userId = user.id, movieId = it.movie.id, reminderDate = it.reminderDate)
                ) }
                    .onSuccess { Log.d("R", "Фильмы к просмотру загрузились", ) }
                    .onFailure { Log.d("R", "Фильмы к просмотру не загрузились: ${it.message}", ) }
            }

            viewModelScope.launch {
                kotlin.runCatching { saveMovieToLocalDBUseCase(
                    RoomMovieInfo(id = it.movie.id, name = it.movie.name, feesWorld = it.movie.feesWorld, feesUsa = it.movie.feesUsa,
                    budget = it.movie.budget, posterUrl = it.movie.posterUrl, worldPremier = it.movie.worldPremier, russiaPremier = it.movie.russiaPremier,
                    kpRating = it.movie.kpRating, imdbRating = it.movie.imdbRating, movieLength = it.movie.movieLength, type = it.movie.type,
                    typeNumber = it.movie.typeNumber, description = it.movie.description, year = it.movie.year, alternativeName = it.movie.alternativeName,
                    enName = it.movie.enName, ageRating = it.movie.ageRating, isSeries = it.movie.isSeries, seriesLength = it.movie.seriesLength,
                    totalSeriesLength = it.movie.totalSeriesLength)
                ) }
                    .onSuccess { Log.d("R", "Фильм из к просмотру загрузились", ) }
                    .onFailure { Log.d("R", "Фильм из к просмотру не загрузились: ${it.message}", ) }
            }

        }
        user.watchedMovies?.forEach {

            viewModelScope.launch {
                kotlin.runCatching { saveWatchedMoviesToLocalDBUseCase(
                    WatchedMovies(userId = user.id, movieId = it.movie.id, dateWatched = it.dateWatched)
                ) }
                    .onSuccess { Log.d("R", "Просмотренные загрузились", ) }
                    .onFailure { Log.d("R", "Просмотренные не загрузились: ${it.message}", ) }
            }

            viewModelScope.launch {
                kotlin.runCatching { saveMovieToLocalDBUseCase(
                    RoomMovieInfo(id = it.movie.id, name = it.movie.name, feesWorld = it.movie.feesWorld, feesUsa = it.movie.feesUsa,
                        budget = it.movie.budget, posterUrl = it.movie.posterUrl, worldPremier = it.movie.worldPremier, russiaPremier = it.movie.russiaPremier,
                        kpRating = it.movie.kpRating, imdbRating = it.movie.imdbRating, movieLength = it.movie.movieLength, type = it.movie.type,
                        typeNumber = it.movie.typeNumber, description = it.movie.description, year = it.movie.year, alternativeName = it.movie.alternativeName,
                        enName = it.movie.enName, ageRating = it.movie.ageRating, isSeries = it.movie.isSeries, seriesLength = it.movie.seriesLength,
                        totalSeriesLength = it.movie.totalSeriesLength)
                ) }
                    .onSuccess { Log.d("R", "Фильм из просмотренных загрузились", ) }
                    .onFailure { Log.d("R", "Фильм из просмотренных не загрузились: ${it.message}", ) }
            }

        }
    }
}