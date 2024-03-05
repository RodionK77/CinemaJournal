package com.example.cinemajournal.data

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cinemajournal.data.API.MoviesApi
import com.example.cinemajournal.data.API.RetrofitService
import com.example.cinemajournal.ui.theme.screens.viewmodels.ItemsCompilationUiState
import com.example.example.MovieInfo
import com.example.example.MovieListResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(private val moviesApi: MoviesApi) {

    //private val retrofitService = RetrofitService()
    //private val moviesApi: MoviesApi = retrofitService.retrofit!!.create(MoviesApi::class.java)

    private var top20Movies: MovieListResponse? = null
    private var searchMovies: MovieListResponse? = null

    //var currentMovie: MovieListResponse? = null

    /*fun refreshMovies(){
        moviesApi.getTop20Movies
            .enqueue(object : Callback<MovieListResponse> {
                override fun onResponse(
                    call: Call<MovieListResponse>,
                    response: Response<MovieListResponse>
                ) {
                    response.body()?.let {
                        top20Movies = it
                    }
                }

                override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                    Log.d("R", "Данные не загрузились", t)
                }
            })
    }*/

    fun refreshSearchMovies(query: String){
        /*moviesApi.getMoviesByName(query = query)
            .enqueue(object : Callback<MovieListResponse> {
                override fun onResponse(
                    call: Call<MovieListResponse>,
                    response: Response<MovieListResponse>
                ) {
                    response.body()?.let {
                        searchMovies = it
                    }
                }

                override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                    Log.d("R", "Данные не загрузились", t)
                }
            })*/
    }

    suspend fun getTop20(): MovieListResponse?{
        return moviesApi.getTop20Movies()
    }

    suspend fun getSearchMovies(query: String): MovieListResponse?{
        return moviesApi.getMoviesByName(query = query)
    }

    suspend fun getFilteredMovies(type: List<String>?, year:String?, rating: String?, ageRating: String?, time: String?, genresName: List<String>?, countriesName: List<String>?): MovieListResponse?{
        return moviesApi.getFilteredMovies(type = type, year = year, rating = rating, ageRating = ageRating, time = time, genresName = genresName, countriesName = countriesName)
    }

    suspend fun getMovieById(id: Int): MovieInfo?{
        return moviesApi.getMovieById(id = id)
    }
}