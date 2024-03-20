package com.example.cinemajournal.ui.theme.screens.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemajournal.Domain.movieUseCases.GetMovieByIdUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.AddMovieToDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.AddMovieToWatchToDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.AddWatchedMovieToDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.CheckMovieToWatchUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.CheckWatchedMovieUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteMovieToWatchByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteMovieToWatchFromDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteWatchedMovieByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteWatchedMovieFromDBUseCase
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatchForRetrofit
import com.example.cinemajournal.data.models.RoomModels.RoomMovieInfoForRetrofit
import com.example.cinemajournal.data.models.RoomModels.User
import com.example.cinemajournal.data.models.RoomModels.WatchedMoviesForRetrofit
import com.example.example.MovieInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ItemDescriptionUiState(
    val movieInfo: MovieInfo? = null,
    val roomMovieInfoForRetrofit: RoomMovieInfoForRetrofit? = null,
    val user: User? = null,
    val movieToWatchStatus: Boolean = false,
    val watchedMovieStatus: Boolean = false,
    val addMovieToDBResponseMessage: String = "",
    val addMovieToWatchDBResponseMessage: String = "",
    val addWatchedMovieToDBResponseMessage: String = "",
    val addMovieToWatchToLocalDBResponseMessage: String = "",
    val addWatchedMovieToLocalDBResponseMessage: String = ""
)

@HiltViewModel
class DescriptionViewModel @Inject constructor(private val getMovieByIdUseCase: GetMovieByIdUseCase,
                                               private val addMovieToDBUseCase: AddMovieToDBUseCase,
                                               private val addMovieToWatchToDBUseCase: AddMovieToWatchToDBUseCase,
                                               private val deleteMovieToWatchFromDBUseCase: DeleteMovieToWatchFromDBUseCase,
                                               private val addWatchedMovieToDBUseCase: AddWatchedMovieToDBUseCase,
                                               private val deleteWatchedMovieFromDBUseCase: DeleteWatchedMovieFromDBUseCase,
                                               private val checkMovieToWatchUseCase: CheckMovieToWatchUseCase,
                                               private val checkWatchedMovieUseCase: CheckWatchedMovieUseCase,
                                               private val deleteMovieToWatchByIdFromLocalDBUseCase: DeleteMovieToWatchByIdFromLocalDBUseCase,
                                               private val deleteWatchedMovieByIdFromLocalDBUseCase: DeleteWatchedMovieByIdFromLocalDBUseCase): ViewModel() {

    var uiState by mutableStateOf(ItemDescriptionUiState())
        private set

    fun refreshCurrentMovieInfo(movieInfo: MovieInfo?) {
        uiState = uiState.copy(movieInfo = movieInfo)
    }
    fun refreshCurrentMovieInfoRoom(movieInfo: RoomMovieInfoForRetrofit?) {
        uiState = uiState.copy(roomMovieInfoForRetrofit = movieInfo)
    }

    fun checkMovieToWatch(movieId: Int, userId: Int){
        viewModelScope.launch {
            delay(250)
            kotlin.runCatching { checkMovieToWatchUseCase(movieId, userId) }
                .onSuccess { response ->
                    uiState = uiState.copy(movieToWatchStatus = response)
                    Log.d("R", "${uiState.movieToWatchStatus}")
                }
                .onFailure { Log.d("R", "Не удалось получить статус фильма к просмотру: ${it.message}", ) }
        }
    }

    fun checkWatchedMovie(movieId: Int, userId: Int){
        viewModelScope.launch {
            delay(250)
            kotlin.runCatching { checkWatchedMovieUseCase(movieId, userId) }
                .onSuccess { response ->
                    uiState = uiState.copy(watchedMovieStatus = response)
                }
                .onFailure { Log.d("R", "Не удалось получить статус просмотренного фильма${it.message}", ) }
        }
    }

    fun getMovieInfo(id: Int){
        //refreshSearchMoviesUseCase(query)
        viewModelScope.launch {
            kotlin.runCatching { getMovieByIdUseCase(id) }
                .onSuccess { response ->
                    uiState = uiState.copy(movieInfo = response)
                }
                .onFailure { Log.d("R", "Данные не загрузились", ) }
        }
        //uiState = uiState.copy(searchMoviesInfo = getSearchMoviesUseCase(query)?.movieInfo ?: arrayListOf())
    }

    fun addMovieToDB(roomMovieInfo: RoomMovieInfoForRetrofit){
        viewModelScope.launch {
            kotlin.runCatching { addMovieToDBUseCase(roomMovieInfo) }
                .onSuccess { response ->
                    //uiState = uiState.copy(movieInfo = response)
                    Log.d("R", response.toString(), )
                }
                .onFailure { Log.d("R", "Проблема при добавлении фильма: ${it.message}", ) }
        }
        //uiState = uiState.copy(searchMoviesInfo = getSearchMoviesUseCase(query)?.movieInfo ?: arrayListOf())
    }

    fun addMovieToWatchToDB(moviesToWatch: MoviesToWatchForRetrofit){
        viewModelScope.launch {
            kotlin.runCatching { addMovieToWatchToDBUseCase(moviesToWatch) }
                .onSuccess { response ->
                    //uiState = uiState.copy(movieInfo = response)
                    Log.d("R", response.toString(), )
                }
                .onFailure { Log.d("R", "Проблема при добавлении фильма к просмотру: ${it.message}", ) }
        }
        //uiState = uiState.copy(searchMoviesInfo = getSearchMoviesUseCase(query)?.movieInfo ?: arrayListOf())
    }

    fun deleteMovieToWatchFromDB(moviesToWatch: MoviesToWatchForRetrofit){
        viewModelScope.launch {
            kotlin.runCatching { deleteMovieToWatchFromDBUseCase(moviesToWatch) }
                .onSuccess { response ->
                    //uiState = uiState.copy(movieInfo = response)
                    Log.d("R", response.toString(), )
                }
                .onFailure { Log.d("R", "Проблема при удалении фильма к просмотру: ${it.message}", ) }
        }
        //uiState = uiState.copy(searchMoviesInfo = getSearchMoviesUseCase(query)?.movieInfo ?: arrayListOf())
    }

    fun addWatchedMovieToDB(watchedMovies: WatchedMoviesForRetrofit){
        viewModelScope.launch {
            kotlin.runCatching { addWatchedMovieToDBUseCase(watchedMovies) }
                .onSuccess { response ->
                    //uiState = uiState.copy(movieInfo = response)
                    Log.d("R", response.toString(), )
                }
                .onFailure { Log.d("R", "Проблема при добавлении просмотренного филмьма: ${it.message}", ) }
        }
        //uiState = uiState.copy(searchMoviesInfo = getSearchMoviesUseCase(query)?.movieInfo ?: arrayListOf())
    }

    fun deleteWatchedMovieFromDB(watchedMovies: WatchedMoviesForRetrofit){
        viewModelScope.launch {
            kotlin.runCatching { deleteWatchedMovieFromDBUseCase(watchedMovies) }
                .onSuccess { response ->
                    //uiState = uiState.copy(movieInfo = response)
                    Log.d("R", response.toString(), )
                }
                .onFailure { Log.d("R", "Проблема при удалении просмотренного фильма: ${it.message}", ) }
        }
        //uiState = uiState.copy(searchMoviesInfo = getSearchMoviesUseCase(query)?.movieInfo ?: arrayListOf())
    }

    fun deleteWatchedMovieFromLocalDB(userId: Int, movieId: Int){
        viewModelScope.launch {
            kotlin.runCatching { deleteWatchedMovieByIdFromLocalDBUseCase(userId, movieId) }
                .onSuccess { response ->
                    //uiState = uiState.copy(movieInfo = response)
                    Log.d("R", "Просмотренный фильм локально удалён", )
                }
                .onFailure { Log.d("R", "Проблема при удалении локального просмотренного фильма: ${it.message}", ) }
        }
        //uiState = uiState.copy(searchMoviesInfo = getSearchMoviesUseCase(query)?.movieInfo ?: arrayListOf())
    }

    fun deleteMovieToWatchFromLocalDB(userId: Int, movieId: Int){
        viewModelScope.launch {
            kotlin.runCatching { deleteMovieToWatchByIdFromLocalDBUseCase(userId, movieId) }
                .onSuccess { response ->
                    //uiState = uiState.copy(movieInfo = response)
                    Log.d("R", "Фильм к просмотру локально удалён", )
                }
                .onFailure { Log.d("R", "Проблема при удалении локального филмьа к просмотру: ${it.message}", ) }
        }
        //uiState = uiState.copy(searchMoviesInfo = getSearchMoviesUseCase(query)?.movieInfo ?: arrayListOf())
    }
}
