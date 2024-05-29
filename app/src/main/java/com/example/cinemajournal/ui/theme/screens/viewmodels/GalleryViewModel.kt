package com.example.cinemajournal.ui.theme.screens.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemajournal.Domain.movieUseCases.GetFilteredMoviesUseCase
import com.example.cinemajournal.Domain.movieUseCases.GetSearchMoviesUseCase
import com.example.cinemajournal.Domain.movieUseCases.GetTop20InterestedKidsMoviesUseCase
import com.example.cinemajournal.Domain.movieUseCases.GetTop20InterestedMoviesUseCase
import com.example.cinemajournal.Domain.movieUseCases.GetTop20KidsMoviesUseCase
import com.example.cinemajournal.Domain.movieUseCases.GetTop20KidsSeriesUseCase
import com.example.cinemajournal.Domain.movieUseCases.GetTop20MoviesUseCase
import com.example.cinemajournal.Domain.movieUseCases.GetTop20SeriesUseCase
import com.example.cinemajournal.Domain.movieUseCases.RefreshMoviesUseCase
import com.example.cinemajournal.Domain.movieUseCases.RefreshSearchMoviesUseCase
import com.example.example.MovieInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ItemsCompilationUiState(
    val topMoviesInfo  : ArrayList<MovieInfo> = arrayListOf(),
    val topInterestedMoviesInfo  : ArrayList<MovieInfo> = arrayListOf(),
    val topSeriesInfo  : ArrayList<MovieInfo> = arrayListOf(),
    val userRole: Int = 0,
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
class GalleryViewModel @Inject constructor (private val refreshMoviesUseCase: RefreshMoviesUseCase,
                                            private val getTop20MoviesUseCase: GetTop20MoviesUseCase,
                                            private val getTop20SeriesUseCase: GetTop20SeriesUseCase,
                                            private val getTop20KidsMoviesUseCase: GetTop20KidsMoviesUseCase,
                                            private val getTop20KidsSeriesUseCase: GetTop20KidsSeriesUseCase,
                                            private val getTop20InterestedMoviesUseCase: GetTop20InterestedMoviesUseCase,
                                            private val getTop20InterestedKidsMoviesUseCase: GetTop20InterestedKidsMoviesUseCase,
                                            private val refreshSearchMoviesUseCase: RefreshSearchMoviesUseCase,
                                            private val getSearchMoviesUseCase: GetSearchMoviesUseCase,
                                            private val getFilteredMoviesUseCase: GetFilteredMoviesUseCase) : ViewModel(){

    var uiState by mutableStateOf(ItemsCompilationUiState())
        private set

    /*init {
        refreshTops()
    }*/

    fun refreshTops(role: Int) {
        if (role == 0){
            viewModelScope.launch {
                kotlin.runCatching { getTop20MoviesUseCase() }
                    .onSuccess { uiState = uiState.copy(topMoviesInfo = it?.movieInfo ?: arrayListOf()) }
                    .onFailure { Log.d("R", "Данные подборки 1 не загрузились: ${it.message}", ) }
                kotlin.runCatching { getTop20InterestedMoviesUseCase() }
                    .onSuccess { uiState = uiState.copy(topInterestedMoviesInfo = it?.movieInfo ?: arrayListOf()) }
                    .onFailure { Log.d("R", "Данные подборки 2 не загрузились: ${it.message}", ) }
                kotlin.runCatching { getTop20SeriesUseCase() }
                    .onSuccess { uiState = uiState.copy(topSeriesInfo = it?.movieInfo ?: arrayListOf()) }
                    .onFailure { Log.d("R", "Данные подборки 3 не загрузились: ${it.message}", ) }
            }
        } else {
            viewModelScope.launch {
                kotlin.runCatching { getTop20KidsMoviesUseCase() }
                    .onSuccess { uiState = uiState.copy(topMoviesInfo = it?.movieInfo ?: arrayListOf()) }
                    .onFailure { Log.d("R", "Данные подборки 1 не загрузились: ${it.message}", ) }
                kotlin.runCatching { getTop20InterestedKidsMoviesUseCase() }
                    .onSuccess { uiState = uiState.copy(topInterestedMoviesInfo = it?.movieInfo ?: arrayListOf()) }
                    .onFailure { Log.d("R", "Данные подборки 2 не загрузились: ${it.message}", ) }
                kotlin.runCatching { getTop20KidsSeriesUseCase() }
                    .onSuccess { uiState = uiState.copy(topSeriesInfo = it?.movieInfo ?: arrayListOf()) }
                    .onFailure { Log.d("R", "Данные подборки 3 не загрузились: ${it.message}", ) }
            }
        }
    }

    fun refreshSearchMovies(query: String){
        viewModelScope.launch {
            kotlin.runCatching { getSearchMoviesUseCase(query) }
                .onSuccess { response ->
                    val sortedMovies = response?.movieInfo?.sortedByDescending { it.rating?.kp } ?: emptyList()
                    //uiState = uiState.copy(searchMoviesInfo = ArrayList(sortedMovies))
                    uiState = uiState.copy(searchMoviesInfo = response?.movieInfo ?: arrayListOf())
                }
                .onFailure { Log.d("R", "Данные поиска не загрузились: ${it.message}", ) }
        }
    }

    fun refreshFilteredMovies(type: List<String>?, year:String?, rating: String?, ageRating: String?, time: String?, genresName: List<String>?, countriesName: List<String>?){
        viewModelScope.launch {
            kotlin.runCatching { getFilteredMoviesUseCase(type = type, year = year, rating = rating, ageRating = ageRating, time = time, genresName = genresName, countriesName = countriesName) }
                .onSuccess { response ->
                    val sortedMovies = response?.movieInfo?.sortedByDescending { it.rating?.kp } ?: emptyList()
                    //uiState = uiState.copy(searchMoviesInfo = ArrayList(sortedMovies))
                    uiState = uiState.copy(searchMoviesInfo = response?.movieInfo ?: arrayListOf())
                }
                .onFailure { Log.d("R", "Данные фильтров не загрузились: ${it.message}", ) }
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