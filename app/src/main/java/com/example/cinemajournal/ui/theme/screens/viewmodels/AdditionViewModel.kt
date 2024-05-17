package com.example.cinemajournal.ui.theme.screens.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemajournal.Domain.moviesDBUseCases.AddMovieToDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.AddMovieToWatchToDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.AddReviewToDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.AddWatchedMovieToDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.CheckMovieToWatchUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.CheckWatchedMovieUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteDislikesByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteLikesByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteMovieToWatchFromDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteWatchedMovieFromDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveCountriesToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveDislikesToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveGenresToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveLikesToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveMovieToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveMovieToWatchToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveReviewToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveWatchedMovieToLocalDBUseCase
import com.example.cinemajournal.data.models.RoomModels.Countries
import com.example.cinemajournal.data.models.RoomModels.CountriesForRetrofit
import com.example.cinemajournal.data.models.RoomModels.Dislikes
import com.example.cinemajournal.data.models.RoomModels.Genres
import com.example.cinemajournal.data.models.RoomModels.GenresForRetrofit
import com.example.cinemajournal.data.models.RoomModels.Likes
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatch
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatchForRetrofit
import com.example.cinemajournal.data.models.RoomModels.Review
import com.example.cinemajournal.data.models.RoomModels.ReviewForRetrofit
import com.example.cinemajournal.data.models.RoomModels.RoomMovieInfo
import com.example.cinemajournal.data.models.RoomModels.RoomMovieInfoForRetrofit
import com.example.cinemajournal.data.models.RoomModels.User
import com.example.cinemajournal.data.models.RoomModels.WatchedMovies
import com.example.cinemajournal.data.models.RoomModels.WatchedMoviesForRetrofit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AdditionUiState(
    val nameText: String = "",
    val yearText: String = "",
    val ratingText: Double = 0.0,
    val timeText: Int = 0,
    val ageRatingText: String = "",
    val budgetText: Int = 0,
    val feesWorldText: Long = 0,
    val descriptionText: String = "",
    var countries: MutableList<CountriesForRetrofit> = mutableListOf(),
    var genres: MutableList<GenresForRetrofit> = mutableListOf(),
    val responseId: Int? = null,
)

@HiltViewModel
class AdditionViewModel @Inject constructor(
    private val addMovieToDBUseCase: AddMovieToDBUseCase,
    private val addMovieToWatchToDBUseCase: AddMovieToWatchToDBUseCase,
    private val deleteMovieToWatchFromDBUseCase: DeleteMovieToWatchFromDBUseCase,
    private val addWatchedMovieToDBUseCase: AddWatchedMovieToDBUseCase,
    private val deleteWatchedMovieFromDBUseCase: DeleteWatchedMovieFromDBUseCase,
    private val checkMovieToWatchUseCase: CheckMovieToWatchUseCase,
    private val checkWatchedMovieUseCase: CheckWatchedMovieUseCase,
    private val saveMovieToWatchToLocalDBUseCase: SaveMovieToWatchToLocalDBUseCase,
    private val saveWatchedMoviesToLocalDBUseCase: SaveWatchedMovieToLocalDBUseCase,
    private val saveMovieToLocalDBUseCase: SaveMovieToLocalDBUseCase,
    private val saveCountriesToLocalDBUseCase: SaveCountriesToLocalDBUseCase,
    private val saveGenresToLocalDBUseCase: SaveGenresToLocalDBUseCase,
) : ViewModel() {


    var uiState by mutableStateOf(AdditionUiState())
        private set

    fun changeNameText(text: String){
        uiState = uiState.copy(nameText = text)
    }
    fun changeYearText(text: String){
        uiState = uiState.copy(yearText = text)
    }
    fun changeRatingText(text: Double){
        uiState = uiState.copy(ratingText = text)
    }
    fun changeTimeText(text: Int){
        uiState = uiState.copy(timeText = text)
    }
    fun changeAgeRatingText(text: String){
        uiState = uiState.copy(ageRatingText = text)
    }
    fun changeBudgetText(text: Int){
        uiState = uiState.copy(budgetText = text)
    }
    fun changeFeesWorldText(text: Long){
        uiState = uiState.copy(feesWorldText = text)
    }
    fun changeDescriptionText(text: String){
        uiState = uiState.copy(descriptionText = text)
    }
    fun changeCountries(countries: MutableList<CountriesForRetrofit>){
        uiState = uiState.copy(countries = countries)
    }
    fun changeGenres(genres: MutableList<GenresForRetrofit>){
        uiState = uiState.copy(genres = genres)
    }
    fun changeId(id: Int?){
        uiState = uiState.copy(responseId = id)
    }

    fun addGenre(genre: GenresForRetrofit){
        uiState.genres.add(genre)
    }
    fun addCountry(country: CountriesForRetrofit){
        uiState.countries.add(country)
    }


    fun changeIdInLists(id: Int){
        for (country in uiState.countries) {
            //country.contentId = id
            country.movieId = id
            country.countriesId = id
        }
        for (genre in uiState.genres) {
            //genre.contentId = id
            genre.movieId = id
            genre.contentId = id
        }
    }

    fun clearAllField(){
        uiState = uiState.copy(nameText = "", yearText = "", ratingText = 0.0, timeText = 0, ageRatingText = "", budgetText = 0, feesWorldText = 0, descriptionText = "", countries = mutableListOf(), genres = mutableListOf(), responseId = null)
    }

    fun addMovieToDB(roomMovieInfo: RoomMovieInfoForRetrofit){
        viewModelScope.launch {
            kotlin.runCatching { addMovieToDBUseCase(roomMovieInfo) }
                .onSuccess { response ->
                    uiState = uiState.copy(responseId = response?.id?:0)
                    Log.d("R", response.toString(), )
                }
                .onFailure { Log.d("R", "Проблема при добавлении фильма: ${it.message}", ) }
        }
    }

    fun addMovieToWatchToDB(moviesToWatch: MoviesToWatchForRetrofit){
        viewModelScope.launch {
            kotlin.runCatching { addMovieToWatchToDBUseCase(moviesToWatch) }
                .onSuccess { response ->
                    Log.d("R", response.toString(), )
                }
                .onFailure { Log.d("R", "Проблема при добавлении фильма к просмотру: ${it.message}", ) }
        }
    }

    fun addWatchedMovieToDB(watchedMovies: WatchedMoviesForRetrofit){
        viewModelScope.launch {
            kotlin.runCatching { addWatchedMovieToDBUseCase(watchedMovies) }
                .onSuccess { response ->
                    Log.d("R", response.toString(), )
                }
                .onFailure { Log.d("R", "Проблема при добавлении просмотренного филмьма: ${it.message}", ) }
        }
    }

    fun addMovieToLocalDB(roomMovieInfo: RoomMovieInfo) {
        viewModelScope.launch {
            kotlin.runCatching { saveMovieToLocalDBUseCase(roomMovieInfo) }
                .onSuccess { response ->
                    Log.d(
                        "R",
                        "Фильм записан в локальную БД",
                    )
                }
                .onFailure {
                    Log.d(
                        "R",
                        "Проблема с записью фильма в локальную бд: ${it.message}",
                    )
                }
        }
    }

    fun addWatchedMovieToLocalDB(watchedMovies: WatchedMovies) {
        viewModelScope.launch {
            kotlin.runCatching { saveWatchedMoviesToLocalDBUseCase(watchedMovies) }
                .onSuccess { response ->
                    Log.d(
                        "R",
                        "Просмотренный записан в локальную БД",
                    )
                }
                .onFailure {
                    Log.d(
                        "R",
                        "Проблема с записью просмотренного в локальную бд: ${it.message}",
                    )
                }
        }
    }

    fun addMovieToWatchToLocalDB(toWatch: MoviesToWatch) {
        viewModelScope.launch {
            kotlin.runCatching { saveMovieToWatchToLocalDBUseCase(toWatch) }
                .onSuccess { response ->
                    Log.d(
                        "R",
                        "К просмотру записан в локальную БД",
                    )
                }
                .onFailure {
                    Log.d(
                        "R",
                        "Проблема с записью к просмотру в локальную бд: ${it.message}",
                    )
                }
        }
    }

    fun writeCountriesToLocalDB(countries: List<com.example.example.Countries>, movieId: Int) {
        countries.forEach {
            viewModelScope.launch {
                kotlin.runCatching {
                    saveCountriesToLocalDBUseCase(
                        Countries(
                            contentId = movieId,
                            name = it.name ?: ""
                        )
                    )
                }
                    .onSuccess { Log.d("R", "Страна записалась") }
                    .onFailure {
                        Log.d(
                            "R",
                            "Страна не записалась: ${it.message}",
                        )
                    }
            }
        }
    }

    fun writeGenresToLocalDB(genres: List<com.example.example.Genres>, movieId: Int) {
        genres.forEach {
            viewModelScope.launch {
                kotlin.runCatching {
                    saveGenresToLocalDBUseCase(Genres(contentId = movieId, name = it.name ?: ""))
                }
                    .onSuccess { Log.d("R", "Жанр записался") }
                    .onFailure {
                        Log.d(
                            "R",
                            "Жанр не записался: ${it.message}",
                        )
                    }
            }
        }
    }

}