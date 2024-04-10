package com.example.cinemajournal.ui.theme.screens.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemajournal.Domain.moviesDBUseCases.AddReviewToDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteDislikesByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteLikesByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveDislikesToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveLikesToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveReviewToLocalDBUseCase
import com.example.cinemajournal.data.models.RoomModels.Dislikes
import com.example.cinemajournal.data.models.RoomModels.Likes
import com.example.cinemajournal.data.models.RoomModels.Review
import com.example.cinemajournal.data.models.RoomModels.ReviewForRetrofit
import com.example.cinemajournal.data.models.RoomModels.RoomMovieInfoForRetrofit
import com.example.cinemajournal.data.models.RoomModels.User
import com.example.example.MovieInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

data class ItemReviewUiState(
    val roomMovieInfoForRetrofit: RoomMovieInfoForRetrofit? = null,
    val user: User? = null,
    val likesForReview: MutableList<Likes>? = null,
    val dislikesForReview: MutableList<Dislikes>? = null,
    val rating: Float? = null,
    val reviewText: String? = null,
    val dateWatched: String? = null
)

@HiltViewModel
class ReviewViewModel @Inject constructor(private val addReviewToDBUseCase: AddReviewToDBUseCase,
                                          private val saveReviewToLocalDBUseCase: SaveReviewToLocalDBUseCase,
                                          private val saveLikesToLocalDBUseCase: SaveLikesToLocalDBUseCase,
                                          private val saveDislikesToLocalDBUseCase: SaveDislikesToLocalDBUseCase,
                                          private val deleteLikesByIdFromLocalDBUseCase: DeleteLikesByIdFromLocalDBUseCase,
                                          private val deleteDislikesByIdFromLocalDBUseCase: DeleteDislikesByIdFromLocalDBUseCase
) : ViewModel() {


    fun refreshCurrentMovieInfoRoom(movieInfo: RoomMovieInfoForRetrofit?) {
        uiState = uiState.copy(roomMovieInfoForRetrofit = movieInfo)
    }

    fun changeRating(rating: Float?){
        uiState = uiState.copy(rating = rating)
    }

    fun changeReviewText(reviewText: String?){
        uiState = uiState.copy(reviewText = reviewText)
    }

    fun changeDate(date: String?){
        uiState = uiState.copy(dateWatched = date)
    }

    var uiState by mutableStateOf(ItemReviewUiState())
        private set

    fun changeLikes(likes: MutableList<Likes>?){
        uiState = uiState.copy(likesForReview = likes)
    }
    fun addLike(like: Likes){
        uiState.likesForReview?.add(like)
    }

    fun changeDislikes(dislikes: MutableList<Dislikes>?){
        uiState = uiState.copy(dislikesForReview = dislikes)
    }
    fun addDislike(dislike: Dislikes){
        uiState.dislikesForReview?.add(dislike)
    }

    fun addReviewToDB(review: ReviewForRetrofit){
        viewModelScope.launch {
            kotlin.runCatching { addReviewToDBUseCase(review) }
                .onSuccess { response ->
                    Log.d("R", "Ревью добавлено в удалённую бд", )
                }
                .onFailure { Log.d("R", "Проблема при добавлении ревью в удалённую бд: ${it.message}", ) }
        }
    }

    fun saveReviewToLocalDB(review: Review){
        viewModelScope.launch {
            kotlin.runCatching { saveReviewToLocalDBUseCase(review) }
                .onSuccess { response ->
                    Log.d("R", "Обновленное ревью записано", )
                }
                .onFailure { Log.d("R", "Обновленное ревью не записано: ${it.message}", ) }
        }
    }

    fun saveLikesToLocalDB(likes: Likes){
        viewModelScope.launch {
            kotlin.runCatching { saveLikesToLocalDBUseCase(likes) }
                .onSuccess { response ->
                    Log.d("R", "Обновленные лайки записаны", )
                }
                .onFailure { Log.d("R", "Обновленные лайки не записаны : ${it.message}", ) }
        }
    }

    fun saveDislikesToLocalDB(dislikes: Dislikes){
        viewModelScope.launch {
            kotlin.runCatching { saveDislikesToLocalDBUseCase(dislikes) }
                .onSuccess { response ->
                    Log.d("R", "Обновленные дизлайки не записаны", )
                }
                .onFailure { Log.d("R", "Обновленные дизлайки не записаны: ${it.message}", ) }
        }
    }

    fun deleteLikesFromLocalDB(movieId: Int){
        viewModelScope.launch {
            kotlin.runCatching { deleteLikesByIdFromLocalDBUseCase(movieId) }
                .onSuccess { response ->
                    //uiState = uiState.copy(movieInfo = response)
                    Log.d("R", "Лайки для фильма удалены", )
                }
                .onFailure { Log.d("R", "Проблема при удалении лайков для фильма: ${it.message}", ) }
        }
    }

    fun deleteDislikesFromLocalDB(movieId: Int){
        viewModelScope.launch {
            kotlin.runCatching { deleteDislikesByIdFromLocalDBUseCase(movieId) }
                .onSuccess { response ->
                    //uiState = uiState.copy(movieInfo = response)
                    Log.d("R", "Дизлайки для фильма удалены", )
                }
                .onFailure { Log.d("R", "Проблема при удалении дизлайков для фильма: ${it.message}", ) }
        }
    }
}