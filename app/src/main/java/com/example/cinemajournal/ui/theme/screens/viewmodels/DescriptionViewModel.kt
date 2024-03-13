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
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteMovieToWatchFromDBUseCase
import com.example.cinemajournal.data.MovieRepository
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatch
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatchForRetrofit
import com.example.cinemajournal.data.models.RoomModels.RoomMovieInfo
import com.example.cinemajournal.data.models.RoomModels.User
import com.example.example.MovieInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ItemDescriptionUiState(
    val movieInfo: MovieInfo? = null,
    val user: User? = null
)

@HiltViewModel
class DescriptionViewModel @Inject constructor(private val getMovieByIdUseCase: GetMovieByIdUseCase,
                                               private val addMovieToDBUseCase: AddMovieToDBUseCase,
                                               private val addMovieToWatchToDBUseCase: AddMovieToWatchToDBUseCase,
                                               private val deleteMovieToWatchFromDBUseCase: DeleteMovieToWatchFromDBUseCase): ViewModel() {

    var uiState by mutableStateOf(ItemDescriptionUiState())
        private set

    fun refreshCurrentMovie(movieInfo: MovieInfo) {
        uiState = uiState.copy(movieInfo = movieInfo)
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

    fun addMovieToDB(roomMovieInfo: RoomMovieInfo){
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
}
