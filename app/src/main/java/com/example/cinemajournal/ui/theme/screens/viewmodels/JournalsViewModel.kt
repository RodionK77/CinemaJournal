package com.example.cinemajournal.ui.theme.screens.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemajournal.Domain.movieUseCases.GetMovieByIdUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.CheckMovieToWatchUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.CheckWatchedMovieUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteAllMoviesFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteMovieByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteMovieToWatchByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteWatchedMovieByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetCountriesByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetDislikesByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetGenresByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetLikesByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetMoviesFromToWatchFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetMoviesFromWatchedFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetPersonsByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetReviewByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetSeasonsInfoByIdFromLocalDBUseCase
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
import com.example.cinemajournal.data.models.RoomModels.CountriesForRetrofit
import com.example.cinemajournal.data.models.RoomModels.Dislikes
import com.example.cinemajournal.data.models.RoomModels.DislikesForRetrofit
import com.example.cinemajournal.data.models.RoomModels.Genres
import com.example.cinemajournal.data.models.RoomModels.GenresForRetrofit
import com.example.cinemajournal.data.models.RoomModels.Likes
import com.example.cinemajournal.data.models.RoomModels.LikesForRetrofit
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatch
import com.example.cinemajournal.data.models.RoomModels.Persons
import com.example.cinemajournal.data.models.RoomModels.PersonsForRetrofit
import com.example.cinemajournal.data.models.RoomModels.Review
import com.example.cinemajournal.data.models.RoomModels.ReviewForRetrofit
import com.example.cinemajournal.data.models.RoomModels.RoomMovieInfo
import com.example.cinemajournal.data.models.RoomModels.RoomMovieInfoForRetrofit
import com.example.cinemajournal.data.models.RoomModels.SeasonsInfo
import com.example.cinemajournal.data.models.RoomModels.SeasonsInfoForRetrofit
import com.example.cinemajournal.data.models.RoomModels.User
import com.example.cinemajournal.data.models.RoomModels.UserForRetrofit
import com.example.cinemajournal.data.models.RoomModels.WatchedMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

data class JournalsUiState(
    val user: UserForRetrofit? = null,
    val selectedTabIndex: Int = 0,
    val moviesToWatch: List<RoomMovieInfoForRetrofit>? = null,
    val watchedMovies: List<RoomMovieInfoForRetrofit>? = null,
    val countries: List<CountriesForRetrofit>? = null,
    val genres: List<GenresForRetrofit>? = null,
    val persons: List<Persons>? = null,
    val seasonsInfo: List<SeasonsInfo>? = null,
    val downloadMoviesStatus: Boolean = false,
    val downloadReviewsStatus: Boolean = false,
    val downloadWatchedMovieStatus: Boolean = false,

    )

@HiltViewModel
class JournalsViewModel @Inject constructor(
    private val getUserFromDBUseCase: GetUserFromDBUseCase,
    private val saveReviewToLocalDBUseCase: SaveReviewToLocalDBUseCase,
    private val saveLikesToLocalDBUseCase: SaveLikesToLocalDBUseCase,
    private val saveDislikesToLocalDBUseCase: SaveDislikesToLocalDBUseCase,
    private val saveMovieToWatchToLocalDBUseCase: SaveMovieToWatchToLocalDBUseCase,
    private val saveWatchedMoviesToLocalDBUseCase: SaveWatchedMovieToLocalDBUseCase,
    private val saveMovieToLocalDBUseCase: SaveMovieToLocalDBUseCase,
    private val saveCountriesToLocalDBUseCase: SaveCountriesToLocalDBUseCase,
    private val saveGenresToLocalDBUseCase: SaveGenresToLocalDBUseCase,
    private val savePersonsToLocalDBUseCase: SavePersonsToLocalDBUseCase,
    private val saveSeasonsInfoToLocalDBUseCase: SaveSeasonsInfoToLocalDBUseCase,
    private val checkMovieToWatchUseCase: CheckMovieToWatchUseCase,
    private val checkWatchedMovieUseCase: CheckWatchedMovieUseCase,
    private val getMoviesFromToWatchFromLocalDBUseCase: GetMoviesFromToWatchFromLocalDBUseCase,
    private val getMoviesFromWatchedFromLocalDBUseCase: GetMoviesFromWatchedFromLocalDBUseCase,
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    private val deleteAllMoviesFromLocalDBUseCase: DeleteAllMoviesFromLocalDBUseCase,
    private val deleteMovieByIdFromLocalDBUseCase: DeleteMovieByIdFromLocalDBUseCase,
    private val deleteWatchedMovieByIdFromLocalDBUseCase: DeleteWatchedMovieByIdFromLocalDBUseCase,
    private val deleteMovieToWatchByIdFromLocalDBUseCase: DeleteMovieToWatchByIdFromLocalDBUseCase,
    private val addMovieToLocalDBUseCase: SaveMovieToLocalDBUseCase,
    private val addWatchedMovieToLocalDBUseCase: SaveWatchedMovieToLocalDBUseCase,
    private val addMovieToWatchToLocalDBUseCase: SaveMovieToWatchToLocalDBUseCase,
    private val getCountriesByIdFromLocalDBUseCase: GetCountriesByIdFromLocalDBUseCase,
    private val getGenresByIdFromLocalDBUseCase: GetGenresByIdFromLocalDBUseCase,
    private val getPersonsByIdFromLocalDBUseCase: GetPersonsByIdFromLocalDBUseCase,
    private val getSeasonsInfoByIdFromLocalDBUseCase: GetSeasonsInfoByIdFromLocalDBUseCase,
    private val getReviewByIdFromLocalDBUseCase: GetReviewByIdFromLocalDBUseCase,
    private val getLikesByIdFromLocalDBUseCase: GetLikesByIdFromLocalDBUseCase,
    private val getDislikesByIdFromLocalDBUseCase: GetDislikesByIdFromLocalDBUseCase,
) : ViewModel() {

    var uiState by mutableStateOf(JournalsUiState())
        private set

    fun changeUser(user: UserForRetrofit?) {
        uiState = uiState.copy(user = user)
    }

    fun changeSelectedTabIndex(id: Int) {
        uiState = uiState.copy(selectedTabIndex = id)
    }

    fun getUserFromDB(id: Int) {
        viewModelScope.launch {
            kotlin.runCatching { getUserFromDBUseCase(id) }
                .onSuccess { response ->
                    uiState = uiState.copy(user = response)
                }
                .onFailure {
                    Log.d(
                        "R",
                        "${id}; Проблема с получением данных пользователя: ${it.message}",
                    )
                }
        }
    }

    fun getMoviesFromToWatchFromLocalDB() {
        var movies: MutableList<RoomMovieInfoForRetrofit> = mutableListOf()
        var countries: List<CountriesForRetrofit> = mutableListOf()
        var genres: List<GenresForRetrofit> = mutableListOf()
        var persons: List<PersonsForRetrofit> = mutableListOf()
        var seasonsInfo: List<SeasonsInfoForRetrofit> = mutableListOf()
        viewModelScope.launch {
            //delay(1000)
            kotlin.runCatching { getMoviesFromToWatchFromLocalDBUseCase() }
                .onSuccess { response ->
                    response?.forEach {
                        viewModelScope.launch {
                            kotlin.runCatching { getCountriesByIdFromLocalDBUseCase(it.id) }
                                .onSuccess { response2 ->
                                    Log.d(
                                        "R",
                                        "Страны для фильма к просмотру получены: ${response2}",
                                    )
                                    countries = response2
                                }
                                .onFailure {
                                    Log.d(
                                        "R",
                                        "Страны для фильма к просмотру не получены: ${it.message}",
                                    )
                                }
                        }
                        viewModelScope.launch {
                            kotlin.runCatching { getGenresByIdFromLocalDBUseCase(it.id) }
                                .onSuccess { response3 ->
                                    Log.d(
                                        "R",
                                        "Жанры для фильма к просмотру получены: ${response3}",
                                    )
                                    genres = response3
                                }
                                .onFailure {
                                    Log.d(
                                        "R",
                                        "Жанры для фильма к просмотру не получены: ${it.message}",
                                    )
                                }
                        }
                        viewModelScope.launch {
                            kotlin.runCatching { getPersonsByIdFromLocalDBUseCase(it.id) }
                                .onSuccess { response4 ->
                                    Log.d(
                                        "R",
                                        "Персоны для фильма к просмотру получены: ${response4}",
                                    )
                                    persons = response4
                                }
                                .onFailure {
                                    Log.d(
                                        "R",
                                        "Персоны для фильма к просмотру не получены: ${it.message}",
                                    )
                                }
                        }
                        viewModelScope.launch {
                            kotlin.runCatching { getSeasonsInfoByIdFromLocalDBUseCase(it.id) }
                                .onSuccess { response5 ->
                                    Log.d(
                                        "R",
                                        "Сезоны для фильма к просмотру получены: ${response5}",
                                    )
                                    seasonsInfo = response5
                                }
                                .onFailure {
                                    Log.d(
                                        "R",
                                        "Сезоны для фильма к просмотру не получены: ${it.message}",
                                    )
                                }
                        }
                        delay(250)
                        movies.add(
                            RoomMovieInfoForRetrofit(
                                id = it.id ?: 0,
                                name = it?.name ?: "",
                                feesWorld = it?.feesWorld ?: 0,
                                feesUsa = it?.feesUsa ?: 0,
                                budget = it?.budget ?: 0,
                                posterUrl = it?.posterUrl ?: "",
                                worldPremier = it?.worldPremier,
                                russiaPremier = it?.russiaPremier,
                                kpRating = it?.kpRating ?: 0.0,
                                imdbRating = it?.imdbRating ?: 0.0,
                                movieLength = it?.movieLength ?: 0,
                                type = it?.type ?: "",
                                typeNumber = it?.typeNumber ?: 0,
                                description = it?.description ?: "",
                                year = it?.year ?: 0,
                                alternativeName = it?.alternativeName ?: "",
                                enName = it?.enName ?: "",
                                ageRating = it?.ageRating.toString(),
                                isSeries = it?.isSeries,
                                seriesLength = it?.seriesLength,
                                totalSeriesLength = it?.totalSeriesLength,
                                countries = countries,
                                genres = genres,
                                persons = persons,
                                seasonsInfo = seasonsInfo,
                            )
                        )
                    }
                    uiState = uiState.copy(moviesToWatch = movies)
                }
                .onFailure {
                    Log.d(
                        "R",
                        "Проблема с получением фильмов к просмотру: ${it.message}",
                    )
                }
        }
    }

    fun getMoviesFromWatchedFromLocalDB() {
        var movies: MutableList<RoomMovieInfoForRetrofit> = mutableListOf()
        var countries: List<CountriesForRetrofit> = mutableListOf()
        var genres: List<GenresForRetrofit> = mutableListOf()
        var persons: List<PersonsForRetrofit> = mutableListOf()
        var seasonsInfo: List<SeasonsInfoForRetrofit> = mutableListOf()
        var review: Review? = null
        var likes: List<Likes> = mutableListOf()
        var dislikes: List<Dislikes> = mutableListOf()
        viewModelScope.launch {
            //delay(1000)
            kotlin.runCatching { getMoviesFromWatchedFromLocalDBUseCase() }
                .onSuccess { response ->
                    response?.forEach {
                        viewModelScope.launch {
                            kotlin.runCatching { getCountriesByIdFromLocalDBUseCase(it.id) }
                                .onSuccess { response2 ->
                                    Log.d(
                                        "R",
                                        "Страны для просмотренного фильма получены",
                                    )
                                    countries = response2
                                }
                                .onFailure {
                                    Log.d(
                                        "R",
                                        "Страны для просмотренного фильма не получены: ${it.message}",
                                    )
                                }
                        }
                        viewModelScope.launch {
                            kotlin.runCatching { getGenresByIdFromLocalDBUseCase(it.id) }
                                .onSuccess { response3 ->
                                    Log.d(
                                        "R",
                                        "Жанры для просмотренного фильма получены",
                                    )
                                    genres = response3
                                }
                                .onFailure {
                                    Log.d(
                                        "R",
                                        "Жанры для просмотренного фильма не получены: ${it.message}",
                                    )
                                }
                        }
                        viewModelScope.launch {
                            kotlin.runCatching { getPersonsByIdFromLocalDBUseCase(it.id) }
                                .onSuccess { response4 ->
                                    Log.d(
                                        "R",
                                        "Персоны для просмотренного фильма получены: ${response4}",
                                    )
                                    persons = response4
                                }
                                .onFailure {
                                    Log.d(
                                        "R",
                                        "Персоны для просмоторенного не получены: ${it.message}",
                                    )
                                }
                        }
                        viewModelScope.launch {
                            kotlin.runCatching { getSeasonsInfoByIdFromLocalDBUseCase(it.id) }
                                .onSuccess { response5 ->
                                    Log.d(
                                        "R",
                                        "Сезоны для просмотренного получены: ${response5}",
                                    )
                                    seasonsInfo = response5
                                }
                                .onFailure {
                                    Log.d(
                                        "R",
                                        "Сезоны для просмотренного не получены: ${it.message}",
                                    )
                                }
                        }
                        viewModelScope.launch {
                            kotlin.runCatching { getReviewByIdFromLocalDBUseCase(it.id) }
                                .onSuccess { response6 ->
                                    Log.d(
                                        "R",
                                        "Ревью для просмотенного получены: ${response6}",
                                    )
                                    review = response6

                                    viewModelScope.launch {
                                        delay(250)
                                        kotlin.runCatching { getLikesByIdFromLocalDBUseCase(it.id) }
                                            .onSuccess { response7 ->
                                                Log.d(
                                                    "R",
                                                    "Лайки для просмотренного получены: ${response7}",
                                                )
                                                likes = response7
                                            }
                                            .onFailure {
                                                Log.d(
                                                    "R",
                                                    "Лайки для просомтренного не получены: ${it.message}",
                                                )
                                            }
                                    }

                                    viewModelScope.launch {
                                        delay(250)
                                        kotlin.runCatching { getDislikesByIdFromLocalDBUseCase(it.id) }
                                            .onSuccess { response8 ->
                                                Log.d(
                                                    "R",
                                                    "Дизлайки для просмотренного получены: ${response8}",
                                                )
                                                dislikes = response8
                                            }
                                            .onFailure {
                                                Log.d(
                                                    "R",
                                                    "Дизлайки просмотренного не получены: ${it.message}",
                                                )
                                            }
                                    }
                                }
                                .onFailure {
                                    Log.d(
                                        "R",
                                        "Ревью для просмотренного не получены: ${it.message}",
                                    )
                                }
                        }
                        delay(500)
                        val movie = RoomMovieInfoForRetrofit(
                            id = it.id ?: 0,
                            name = it?.name ?: "",
                            feesWorld = it?.feesWorld ?: 0,
                            feesUsa = it?.feesUsa ?: 0,
                            budget = it?.budget ?: 0,
                            posterUrl = it?.posterUrl ?: "",
                            worldPremier = it?.worldPremier,
                            russiaPremier = it?.russiaPremier,
                            kpRating = it?.kpRating ?: 0.0,
                            imdbRating = it?.imdbRating ?: 0.0,
                            movieLength = it?.movieLength ?: 0,
                            type = it?.type ?: "",
                            typeNumber = it?.typeNumber ?: 0,
                            description = it?.description ?: "",
                            year = it?.year ?: 0,
                            alternativeName = it?.alternativeName ?: "",
                            enName = it?.enName ?: "",
                            ageRating = it?.ageRating.toString(),
                            isSeries = it?.isSeries,
                            seriesLength = it?.seriesLength,
                            totalSeriesLength = it?.totalSeriesLength,
                            countries = countries,
                            genres = genres,
                            persons = persons,
                            seasonsInfo = seasonsInfo,
                            review = ReviewForRetrofit(
                                user = User(id = uiState.user!!.id),
                                movie = null,
                                contentId = it.id,
                                rating = review?.rating ?: 0.0,
                                notes = review?.notes ?: "",
                                likes = likes,
                                dislikes = dislikes
                            )
                        )
                        movies.add(movie)
                        Log.d(
                            "R",
                            "Тот фильм: $movie",
                        )
                        Log.d(
                            "R",
                            "Ревью того фильма: ${movie.review}",
                        )
                        countries = mutableListOf()
                        genres = mutableListOf()
                        persons = mutableListOf()
                        seasonsInfo = mutableListOf()
                        review = null
                        likes = emptyList()
                        dislikes = emptyList()
                    }
                    uiState = uiState.copy(watchedMovies = movies)
                }
                .onFailure {
                    Log.d(
                        "R",
                        "Проблема с получением просмотренных фильмов: ${it.message}",
                    )
                }
        }
    }

    fun deleteAllMoviesFromLocalDB() {
        viewModelScope.launch {
            kotlin.runCatching { deleteAllMoviesFromLocalDBUseCase() }
                .onSuccess { response ->
                    Log.d(
                        "R",
                        "Фильмы удалены",
                    )
                }
                .onFailure {
                    Log.d(
                        "R",
                        "Проблема с удалением фильмов: ${it.message}",
                    )
                }
        }
    }

    fun deleteMovieByIdFromLocalDB(id: Int) {
        viewModelScope.launch {
            kotlin.runCatching { deleteMovieByIdFromLocalDBUseCase(id) }
                .onSuccess { response ->
                    Log.d(
                        "R",
                        "Фильм удален",
                    )
                }
                .onFailure {
                    Log.d(
                        "R",
                        "Проблема с удалением фильма: ${it.message}",
                    )
                }
        }
    }

    fun deleteWatchedMovieByIdFromLocalDB(movieId: Int, userId: Int) {
        viewModelScope.launch {
            kotlin.runCatching { deleteWatchedMovieByIdFromLocalDBUseCase(movieId, userId) }
                .onSuccess { response ->
                    Log.d(
                        "R",
                        "Просмотренный удален",
                    )
                }
                .onFailure {
                    Log.d(
                        "R",
                        "Проблема с удалением просмотренного: ${it.message}",
                    )
                }
        }
    }

    fun addMovieToLocalDB(roomMovieInfo: RoomMovieInfo) {
        viewModelScope.launch {
            kotlin.runCatching { addMovieToLocalDBUseCase(roomMovieInfo) }
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
            kotlin.runCatching { addWatchedMovieToLocalDBUseCase(watchedMovies) }
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
            kotlin.runCatching { addMovieToWatchToLocalDBUseCase(toWatch) }
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

    /*fun getCountriesFromLocalDB(movieId: Int){
        viewModelScope.launch {
            kotlin.runCatching { getCountriesByIdFromLocalDBUseCase(movieId) }
                .onSuccess { response ->
                    Log.d(
                        "R",
                        "Страны для фильма получены",
                    )
                    uiState = uiState.copy(countries = response)
                }
                .onFailure {
                    Log.d(
                        "R",
                        "Страны для фильма не получены: ${it.message}",
                    )
                }
        }
    }*/

    fun setCountries(countries: List<CountriesForRetrofit>?) {
        uiState = uiState.copy(countries = countries)
    }

    /*fun getGenresFromLocalDB(movieId: Int){
        viewModelScope.launch {
            kotlin.runCatching { getGenresByIdFromLocalDBUseCase(movieId) }
                .onSuccess { response ->
                    Log.d(
                        "R",
                        "Жанры для фильма получены",
                    )
                    uiState = uiState.copy(genres = response)
                }
                .onFailure {
                    Log.d(
                        "R",
                        "Жанры для фильма не получены: ${it.message}",
                    )
                }
        }
    }*/

    fun setGenres(genres: List<GenresForRetrofit>?) {
        uiState = uiState.copy(genres = genres)
    }

    fun startUpdateLocalDB(user: UserForRetrofit) {

        Log.d(
            "R",
            "Заходим в стартовый апдейт",
        )

        viewModelScope.launch {
            loadAndWriteMovies(user)

            while (!uiState.downloadMoviesStatus) {
                Log.d(
                    "R",
                    "ждём загрузки фильмов",
                )
                delay(50)
            }

            writeWatchedMovies(user)

            writeMoviesToWatch(user)

            while (!uiState.downloadWatchedMovieStatus) {
                Log.d(
                    "R",
                    "ждём загрузки просмотренных",
                )
                delay(50)
            }

            writeReviews(user)
        }

    }

    fun loadAndWriteMovies(user: UserForRetrofit) {
        Log.d(
            "R",
            "уникальные фильмы: ${user.getAllUniqueMovies()}",
        )
        uiState = uiState.copy(downloadMoviesStatus = false)
        user.getAllUniqueMovies().forEach { movieId ->
            viewModelScope.launch {
                kotlin.runCatching {
                    getMovieByIdUseCase(
                        movieId
                    )
                }
                    .onSuccess { movieInfo ->
                        Log.d("R", "Фильм из просмотренных загрузился: ${movieInfo!!.id}")
                        viewModelScope.launch {
                            kotlin.runCatching {
                                saveMovieToLocalDBUseCase(
                                    RoomMovieInfo(
                                        id = movieInfo?.id ?: 0,
                                        name = movieInfo?.name ?: "",
                                        feesWorld = movieInfo?.fees?.world?.value ?: 0,
                                        feesUsa = movieInfo?.fees?.usa?.value ?: 0,
                                        budget = movieInfo?.budget?.value ?: 0,
                                        posterUrl = movieInfo?.poster?.url ?: "",
                                        worldPremier = movieInfo?.premiere?.world ?: "",
                                        russiaPremier = movieInfo?.premiere?.russia ?: "",
                                        kpRating = movieInfo?.rating?.kp ?: 0.0,
                                        imdbRating = movieInfo?.rating?.imdb ?: 0.0,
                                        movieLength = movieInfo?.movieLength ?: 0,
                                        type = movieInfo?.type ?: "",
                                        typeNumber = movieInfo?.typeNumber ?: 0,
                                        description = movieInfo?.description ?: "",
                                        year = movieInfo?.year ?: 0,
                                        alternativeName = movieInfo?.alternativeName ?: "",
                                        enName = movieInfo?.enName ?: "",
                                        ageRating = movieInfo?.ageRating.toString(),
                                        isSeries = movieInfo?.isSeries,
                                        seriesLength = movieInfo?.seriesLength,
                                        totalSeriesLength = movieInfo?.totalSeriesLength
                                    )
                                )
                            }
                                .onSuccess {
                                    Log.d("R", "Фильм записался")
                                    writeCountriesToLocalDB(
                                        movieInfo?.countries ?: emptyList(),
                                        movieInfo.id!!
                                    )
                                    writeGenresToLocalDB(
                                        movieInfo?.genres ?: emptyList(),
                                        movieInfo.id!!
                                    )
                                    writePersonsToLocalDB(
                                        movieInfo?.persons ?: emptyList(),
                                        movieInfo.id!!
                                    )
                                    writeSeasonsInfoToLocalDB(
                                        movieInfo?.seasonsInfo ?: emptyList(),
                                        movieInfo.id!!
                                    )
                                    if (movieId == user.getAllUniqueMovies().last()) {
                                        uiState = uiState.copy(downloadMoviesStatus = true)
                                        Log.d(
                                            "R",
                                            "стейт: ${uiState.downloadMoviesStatus}",
                                        )
                                    }
                                }
                                .onFailure {
                                    Log.d(
                                        "R",
                                        "Фильм не записался: ${it.message}",
                                    )
                                    if (movieId == user.getAllUniqueMovies().last()) {
                                        uiState = uiState.copy(downloadMoviesStatus = true)
                                    }
                                }
                        }
                    }
                    .onFailure {
                        viewModelScope.launch {
                            saveMovieToLocalDBUseCase(
                                RoomMovieInfo(
                                    id = movieId,
                                )
                            )
                        }
                        Log.d("R", "Фильм не загрузился: ${it.message}")
                        if (movieId == user.getAllUniqueMovies().last()) {
                            uiState = uiState.copy(downloadMoviesStatus = true)
                        }
                    }
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

    fun writePersonsToLocalDB(persons: List<com.example.example.Persons>, movieId: Int) {
        persons.forEach {
            viewModelScope.launch {
                kotlin.runCatching {
                    savePersonsToLocalDBUseCase(
                        Persons(
                            personsId = it.id!!,
                            contentId = movieId,
                            photo = it.photo,
                            name = it.name,
                            enName = it.enName,
                            description = it.description,
                            profession = it.profession,
                            enProfession = it.enProfession
                        )
                    )
                }
                    .onSuccess { Log.d("R", "Персона записалась") }
                    .onFailure {
                        Log.d(
                            "R",
                            "Персгна не заисалась: ${it.message}",
                        )
                    }
            }
        }
    }

    fun writeSeasonsInfoToLocalDB(
        seasonsInfo: List<com.example.cinemajournal.data.models.SeasonsInfo>,
        movieId: Int
    ) {
        seasonsInfo.forEach {
            viewModelScope.launch {
                kotlin.runCatching {
                    saveSeasonsInfoToLocalDBUseCase(
                        SeasonsInfo(
                            contentId = movieId,
                            number = it.number ?: 0,
                            episodesCount = it.episodesCount ?: 0
                        )
                    )
                }
                    .onSuccess { Log.d("R", "Сезон записался") }
                    .onFailure {
                        Log.d(
                            "R",
                            "Сезон не записался: ${it.message}",
                        )
                    }
            }
        }
    }

    fun writeWatchedMovies(user: UserForRetrofit) {

        uiState = uiState.copy(downloadWatchedMovieStatus = false)
        user.watchedMovies?.forEach { watchedMovie ->

            Log.d("R", "Просмотренный фильм: ${watchedMovie.contentId}")

            viewModelScope.launch {
                delay(250)
                kotlin.runCatching {
                    saveWatchedMoviesToLocalDBUseCase(
                        WatchedMovies(
                            userId = user.id,
                            movieId = watchedMovie.contentId ?: 0,
                            dateWatched = watchedMovie.dateWatched
                        )
                    )
                }
                    .onSuccess {
                        Log.d("R", "Просмотренные загрузились")
                        if (watchedMovie == user.watchedMovies?.last()) {
                            uiState = uiState.copy(downloadWatchedMovieStatus = true)
                        }
                    }
                    .onFailure {
                        Log.d("R", "Просмотренные не загрузились: ${it.message}")
                        if (watchedMovie == user.watchedMovies?.last()) {
                            uiState = uiState.copy(downloadWatchedMovieStatus = true)
                        }
                    }
            }

        }

    }

    fun writeMoviesToWatch(user: UserForRetrofit) {
        if (uiState.downloadMoviesStatus) {
            user.moviesToWatches?.forEach {

                viewModelScope.launch {
                    delay(250)
                    kotlin.runCatching {
                        saveMovieToWatchToLocalDBUseCase(
                            MoviesToWatch(
                                userId = user.id,
                                movieId = it.contentId ?: 0,
                                reminderDate = it.reminderDate
                            )
                        )
                    }
                        .onSuccess { Log.d("R", "Фильмы к просмотру загрузились") }
                        .onFailure {
                            Log.d(
                                "R",
                                "Фильмы к просмотру не загрузились: ${it.message}"
                            )
                        }
                }
            }
        }

    }

    fun writeReviews(user: UserForRetrofit) {
        Log.d("R", "РРРевью: ${user.review}")

        user.review?.forEach {

            viewModelScope.launch {
                //delay(600)
                kotlin.runCatching {
                    saveReviewToLocalDBUseCase(
                        Review(
                            userId = user.id,
                            movieId = it.contentId,
                            rating = it.rating,
                            notes = it.notes
                        )
                    )
                }
                    .onSuccess { Log.d("R", "Ревью записались: ${it}") }
                    .onFailure { Log.d("R", "Ревью не записались: ${it.message}") }
            }

            it.dislikes?.forEach {

                viewModelScope.launch {
                    //delay(600)
                    kotlin.runCatching {
                        saveDislikesToLocalDBUseCase(
                            Dislikes(
                                dislikesId = it.dislikesId ?: 0,
                                userId = user.id,
                                movieId = it.contentId ?: 0,
                                description = it.description
                            )
                        )
                    }
                        .onSuccess { Log.d("R", "Дизлайки записались") }
                        .onFailure { Log.d("R", "Дизлайки не записались: ${it.message}") }
                }

            }
            it.likes?.forEach {

                viewModelScope.launch {
                    //delay(600)
                    kotlin.runCatching {
                        saveLikesToLocalDBUseCase(
                            Likes(
                                likesId = it.likesId ?: 0,
                                userId = user.id,
                                movieId = it.contentId ?: 0,
                                description = it.description
                            )
                        )
                    }
                        .onSuccess { Log.d("R", "Лайки загрузились") }
                        .onFailure { Log.d("R", "Лайки не загрузились: ${it.message}") }
                }

            }
        }

    }
}