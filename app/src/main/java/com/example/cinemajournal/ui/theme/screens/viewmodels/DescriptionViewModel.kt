package com.example.cinemajournal.ui.theme.screens.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemajournal.Domain.movieUseCases.GetMovieByIdUseCase
import com.example.cinemajournal.data.MovieRepository
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
class DescriptionViewModel @Inject constructor(private val getMovieByIdUseCase: GetMovieByIdUseCase): ViewModel() {

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
}
