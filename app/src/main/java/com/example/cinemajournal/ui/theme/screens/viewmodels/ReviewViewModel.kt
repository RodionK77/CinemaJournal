package com.example.cinemajournal.ui.theme.screens.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.cinemajournal.data.models.RoomModels.User
import com.example.example.MovieInfo

data class ItemReviewUiState(
    val movieInfo: MovieInfo? = null,
    val user: User? = null
)


class ReviewViewModel : ViewModel() {

    var uiState by mutableStateOf(ItemReviewUiState())
        private set
}