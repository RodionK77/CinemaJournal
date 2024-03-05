package com.example.cinemajournal.ui.theme.screens.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.cinemajournal.data.models.RoomModels.User

data class JournalsUiState(
    val user: User? = null
)


class JournalsViewModel : ViewModel() {

    var uiState by mutableStateOf(JournalsUiState())
        private set
}