package com.example.cinemajournal.ui.theme.screens.viewmodels

import android.util.Log
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemajournal.Domain.movieUseCases.GetFilteredMoviesUseCase
import com.example.cinemajournal.Domain.movieUseCases.GetSearchMoviesUseCase
import com.example.cinemajournal.Domain.movieUseCases.GetTop20MoviesUseCase
import com.example.cinemajournal.Domain.movieUseCases.RefreshMoviesUseCase
import com.example.cinemajournal.Domain.movieUseCases.RefreshSearchMoviesUseCase
import com.example.example.MovieInfo
import com.google.android.play.integrity.internal.t
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ItemsCompilationUiState(
    val topMoviesInfo  : ArrayList<MovieInfo> = arrayListOf(),
    val selectedMovieId: Int = 0,
    val isSearch: Boolean = false,
    val searchQuery: String = "",
    val isQueryGo: Boolean = false,
    val searchMoviesInfo  : ArrayList<MovieInfo> = arrayListOf(),
    val drawerState: Boolean = false,
    val genresList:MutableList<String> = mutableListOf(),
    val countriesList:MutableList<String> = mutableListOf(),
    val typesList:MutableList<String> = mutableListOf()
)

@HiltViewModel
class GalleryViewModel @Inject constructor (val refreshMoviesUseCase: RefreshMoviesUseCase,
                                            val getTop20MoviesUseCase: GetTop20MoviesUseCase,
                                            val refreshSearchMoviesUseCase: RefreshSearchMoviesUseCase,
                                            val getSearchMoviesUseCase: GetSearchMoviesUseCase,
                                            val getFilteredMoviesUseCase: GetFilteredMoviesUseCase) : ViewModel(){

    var uiState by mutableStateOf(ItemsCompilationUiState())
        private set

    init {
        refreshTops()
    }

    fun refreshTops() {
        viewModelScope.launch {
            kotlin.runCatching { getTop20MoviesUseCase() }
                .onSuccess { uiState = uiState.copy(topMoviesInfo = it?.movieInfo ?: arrayListOf()) }
                .onFailure { Log.d("R", "Данные не загрузились", ) }
        }
    }

    fun refreshSearchMovies(query: String){
        //refreshSearchMoviesUseCase(query)
        viewModelScope.launch {
            kotlin.runCatching { getSearchMoviesUseCase(query) }
                .onSuccess { response ->
                    val sortedMovies = response?.movieInfo?.sortedByDescending { it.rating?.kp } ?: emptyList()
                    //uiState = uiState.copy(searchMoviesInfo = ArrayList(sortedMovies))
                    uiState = uiState.copy(searchMoviesInfo = response?.movieInfo ?: arrayListOf())
                }
                .onFailure { Log.d("R", "Данные не загрузились", ) }
        }
        //uiState = uiState.copy(searchMoviesInfo = getSearchMoviesUseCase(query)?.movieInfo ?: arrayListOf())
    }

    fun refreshFilteredMovies(type: List<String>?, year:String?, rating: String?, ageRating: String?, time: String?, genresName: List<String>?, countriesName: List<String>?){
        viewModelScope.launch {
            kotlin.runCatching { getFilteredMoviesUseCase(type = type, year = year, rating = rating, ageRating = ageRating, time = time, genresName = genresName, countriesName = countriesName) }
                .onSuccess { response ->
                    val sortedMovies = response?.movieInfo?.sortedByDescending { it.rating?.kp } ?: emptyList()
                    //uiState = uiState.copy(searchMoviesInfo = ArrayList(sortedMovies))
                    uiState = uiState.copy(searchMoviesInfo = response?.movieInfo ?: arrayListOf())
                }
                .onFailure { Log.d("R", "Данные не загрузились", ) }
        }
    }

    fun selectMovie(id: Int){
        uiState = uiState.copy(selectedMovieId = id)
    }

    fun changeSearchState(b: Boolean){
        uiState = uiState.copy(isSearch = b)
    }

    fun changeSearchQuery(s: String){
        uiState = uiState.copy(searchQuery = s)
    }
    fun changeQueryGo(b: Boolean){
        uiState = uiState.copy(isQueryGo = b)
    }

    fun changeDrawerState(b: Boolean){
        uiState = uiState.copy(drawerState = b)
    }

    fun addGenre(s: String){
        uiState.genresList.add(s)
    }

    fun removeGenre(s: String){
        uiState.genresList.removeIf { it == s }
    }

    fun removeAllGenres(){
        uiState = uiState.copy(genresList = mutableListOf())
    }

    fun addCountry(s: String){
        uiState.countriesList.add(s)
    }

    fun removeAllCountries(){
        uiState = uiState.copy(countriesList = mutableListOf())
    }

    fun removeCountry(s: String){
        uiState.countriesList.removeIf { it == s }
    }
}