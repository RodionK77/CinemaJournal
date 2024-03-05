package com.example.cinemajournal.ui.theme.screens.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemajournal.Domain.userUseCases.CheckMovieToWatchUseCase
import com.example.cinemajournal.Domain.userUseCases.CheckWatchedMovieUseCase
import com.example.cinemajournal.Domain.userUseCases.DeleteMovieToWatchByIdUseCase
import com.example.cinemajournal.Domain.userUseCases.DeleteUserByIdUseCase
import com.example.cinemajournal.Domain.userUseCases.DeleteWatchedMovieByIdUseCase
import com.example.cinemajournal.Domain.userUseCases.GetAllUsersUseCase
import com.example.cinemajournal.Domain.userUseCases.SaveMovieToWatchToDatabaseUseCase
import com.example.cinemajournal.Domain.userUseCases.SaveUserToDatabaseUseCase
import com.example.cinemajournal.Domain.userUseCases.SaveWatchedMovieToDatabaseUseCase
import com.example.cinemajournal.Domain.userUseCases.SignInUserUseCase
import com.example.cinemajournal.Domain.userUseCases.SignOutUserUseCase
import com.example.cinemajournal.Domain.userUseCases.SignUpUserUseCase
import com.example.cinemajournal.data.models.AuthResponse
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatch
import com.example.cinemajournal.data.models.RoomModels.User
import com.example.cinemajournal.data.models.RoomModels.WatchedMovies
import com.example.cinemajournal.data.models.SignInRequest
import com.example.cinemajournal.data.models.SignUpRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EntranceUiState(
    val user: User? = null,
    val allUsers: List<User> = emptyList(),
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance(),
    val isLoginScreen: Boolean = false,
    val isLoginProcess: Boolean = false,
    val isRefreshLoginProcess: Boolean = false,
    val buttonRegistrationState: Boolean = false,
    val buttonLoginState: Boolean = false,
    val emailText: String = "",
    val passwordText: String = "",
    val returnMessage: String = ""
)

@HiltViewModel
class AuthViewModel @Inject constructor (
    private val signUpUserUseCase: SignUpUserUseCase,
    private val signInUserUseCase: SignInUserUseCase,
    private val signOutUserUseCase: SignOutUserUseCase,
    private val checkMovieToWatchUseCase: CheckMovieToWatchUseCase,
    private val checkWatchedMovieUseCase: CheckWatchedMovieUseCase,
    private val deleteMovieToWatchByIdUseCase: DeleteMovieToWatchByIdUseCase,
    private val deleteUserByIdUseCase: DeleteUserByIdUseCase,
    private val deleteWatchedMovieByIdUseCase: DeleteWatchedMovieByIdUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val saveMovieToWatchToDatabaseUseCase: SaveMovieToWatchToDatabaseUseCase,
    private val saveUserToDatabaseUseCase: SaveUserToDatabaseUseCase,
    private val saveWatchedMovieToDatabaseUseCase: SaveWatchedMovieToDatabaseUseCase) : ViewModel() {

    //private val repository: UsersRepository = UsersRepository(database)
    val allUsersLiveData = MutableLiveData<List<User>>()

    /*init {
        getAllUser()
    }*/

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

    val coroutineExceptionHandler = CoroutineExceptionHandler{_, t -> {
        //t.printStackTrace()
        //showErrorOrSomething()
        Log.d("R", "ERROR: ${t.message}",)
    }}

    fun signUpUser(request: SignUpRequest) {
        var message: String = ""
        viewModelScope.launch {
            /*signUpUserUseCase(request).let {
                if(it.isSuccessful){
                    Log.d("R", "ЗАХОДИМ В САККЕС",)
                    uiState = uiState.copy(returnMessage = it.body()?.message ?: "")
                    Log.d("R", "он саккес: ${uiState.returnMessage}",)
                } else {
                    Log.d("R", "ЗАХОДИМ В ФАИЛУР",)
                    uiState = uiState.copy(returnMessage = it.code().toString())
                    Log.d("R", "он фаилур: ${uiState.returnMessage}",)
                }
            }*/

            kotlin.runCatching {
                signUpUserUseCase(request) }
                .onSuccess { response ->
                    Log.d("R", "ЗАХОДИМ В САККЕС",)
                    uiState = uiState.copy(returnMessage = response.message ?: "")
                    //Log.d("R", "он саккес: ${uiState.returnMessage}",)
                }
                .onFailure {
                    Log.d("R", "ЗАХОДИМ В ФАИЛУР",)
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
                    uiState = uiState.copy(user = User(id = response.id.toString(), username = response.username?: "", email = response.email?: "", role = response.role?: 0))
                    message = response
                    Log.d("R", message.email?: "empty", )
                    uiState = uiState.copy(isLoginProcess = false)
                }
                .onFailure {
                    message.message = it.message
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

    fun getUser(): FirebaseUser?{
        return uiState.mAuth.currentUser
    }

    /*fun exitUser(){
        uiState.mAuth.signOut()
    }*/

    /*fun refreshUser(){
        uiState = uiState.copy(user = getUser())
    }*/

    fun saveUserToDatabase(user: User) {
        viewModelScope.launch {
            saveUserToDatabaseUseCase(user)
        }
    }
    fun getAllUser() {
        Log.d("R", "Cтарт юзерс", )
        uiState = uiState.copy(isRefreshLoginProcess = true)
        viewModelScope.launch {
            val users = getAllUsersUseCase()
            Log.d("R", users.toString(), )
            if(users.isNotEmpty()){
                uiState = uiState.copy(user = users[0])
            }
            //uiState = uiState.copy(isRefreshLoginProcess = false)
        }
    }
    fun deleteUserById(id: String){
        viewModelScope.launch {
            deleteUserByIdUseCase(id)
        }
    }

    fun saveWatchedMovieToDatabase(item: WatchedMovies) {
        viewModelScope.launch {
            saveWatchedMovieToDatabaseUseCase(item)
        }
    }
    fun deleteWatchedMovieById(id: Long) {
        viewModelScope.launch {
            deleteWatchedMovieByIdUseCase(id)
        }
    }
    fun checkWatchedMovie(movieId: Long, userId: String) : Boolean{
        return checkWatchedMovieUseCase(movieId, userId)
    }

    fun saveMovieToWatchToDatabase(item: MoviesToWatch) {
        viewModelScope.launch {
            saveMovieToWatchToDatabaseUseCase(item)
        }
    }
    fun deleteMovieToWatchById(id: Long) {
        viewModelScope.launch {
            deleteMovieToWatchByIdUseCase(id)
        }
    }
    fun checkMovieToWatch(movieId: Long, userId: String) : Boolean{
        return checkMovieToWatchUseCase(movieId, userId)
    }

}