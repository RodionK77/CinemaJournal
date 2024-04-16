package com.example.cinemajournal.ui.theme.screens.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemajournal.Domain.userUseCases.DeleteUserByIdUseCase
import com.example.cinemajournal.Domain.userUseCases.GetAllUsersUseCase
import com.example.cinemajournal.Domain.userUseCases.SaveUserToDatabaseUseCase
import com.example.cinemajournal.Domain.userUseCases.SignInUserUseCase
import com.example.cinemajournal.Domain.userUseCases.SignOutUserUseCase
import com.example.cinemajournal.Domain.userUseCases.SignUpUserUseCase
import com.example.cinemajournal.data.models.AuthResponse
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatch
import com.example.cinemajournal.data.models.RoomModels.User
import com.example.cinemajournal.data.models.RoomModels.WatchedMovies
import com.example.cinemajournal.data.models.SignInRequest
import com.example.cinemajournal.data.models.SignUpRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EntranceUiState(
    val user: User? = null,
    val allUsers: List<User> = emptyList(),
    val isLoginScreen: Boolean = false,
    val isLoginProcess: Boolean = false,
    val isRefreshLoginProcess: Boolean = false,
    val buttonRegistrationState: Boolean = false,
    val buttonLoginState: Boolean = false,
    val emailText: String = "",
    val passwordText: String = "",
    val checkedState: Boolean = false,
    val returnMessage: String = ""
)

@HiltViewModel
class AuthViewModel @Inject constructor (
    private val signUpUserUseCase: SignUpUserUseCase,
    private val signInUserUseCase: SignInUserUseCase,
    private val signOutUserUseCase: SignOutUserUseCase,
    private val deleteUserByIdUseCase: DeleteUserByIdUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val saveUserToDatabaseUseCase: SaveUserToDatabaseUseCase, ) : ViewModel() {

    var uiState by mutableStateOf(EntranceUiState())
        private set

    fun changeScreenState(b: Boolean){
        uiState = uiState.copy(isLoginScreen = b)
    }

    fun changeButtonRegistrationState(b: Boolean){
        uiState = uiState.copy(buttonRegistrationState = b)
    }

    fun changeButtonLoginState(b: Boolean){
        uiState = uiState.copy(buttonLoginState = b)
    }

    fun changeCheckedState(b: Boolean){
        uiState = uiState.copy(checkedState = b)
    }


    fun changeReturnMessage(s: String){
        uiState = uiState.copy(returnMessage = "")
    }

    fun changeEmailText(s: String){
        uiState = uiState.copy(emailText = s)
    }

    fun changePasswordText(s: String){
        uiState = uiState.copy(passwordText = s)
    }

    fun changeUser(user: User?){
        uiState = uiState.copy(user = user)
    }

    fun signUpUser(request: SignUpRequest) {
        viewModelScope.launch {
            kotlin.runCatching {
                signUpUserUseCase(request) }
                .onSuccess { response ->
                    Log.d("R", "Пользователь успешно зарегестрирвован ",)
                    uiState = uiState.copy(returnMessage = response.message ?: "")
                }
                .onFailure {
                    Log.d("R", "Проблема при регистрации пользователя",)
                    uiState = uiState.copy(returnMessage = it.message ?: "")
                    //Log.d("R", "он фаилур: ${uiState.returnMessage}",)
                }
        }
    }


    fun signInUser(request: SignInRequest): AuthResponse {
        uiState = uiState.copy(isLoginProcess = true)
        var message = AuthResponse()
        viewModelScope.launch {
            kotlin.runCatching { signInUserUseCase(request) }
                .onSuccess { response ->
                    uiState = uiState.copy(user = User(id = response.id!!, username = response.username?: "", email = response.email?: "", role = response.role?: 0))
                    message = response
                    Log.d("R", "Пользователь успешно вошёл ",)
                    Log.d("R", message.email?: "empty", )
                    uiState = uiState.copy(isLoginProcess = false)
                }
                .onFailure {
                    message.message = it.message
                    Log.d("R", "Проблема при входе пользователя",)
                    Log.d("R", message!!.message?: "empty", )
                    uiState = uiState.copy(isLoginProcess = false)
                }
        }
        return message
    }

    fun signOutUser() {
        viewModelScope.launch {
            kotlin.runCatching { signOutUserUseCase() }
                .onSuccess { response ->
                    Log.d("R", response.message?: "empty", )
                }
                .onFailure {
                    Log.d("R", it.message?: "empty", )
                }
        }
    }

    fun saveUserToDatabase(user: User) {
        viewModelScope.launch {
            saveUserToDatabaseUseCase(user)
        }
    }
    fun getAllUser() {
        uiState = uiState.copy(isRefreshLoginProcess = true)
        viewModelScope.launch {
            val users = getAllUsersUseCase()
            Log.d("R", users.toString(), )
            if(users.isNotEmpty()){
                uiState = uiState.copy(user = users[0])
            }
        }
    }
    fun deleteUserById(id: Int){
        viewModelScope.launch {
            deleteUserByIdUseCase(id)
        }
    }

}