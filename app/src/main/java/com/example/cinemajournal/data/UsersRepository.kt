package com.example.cinemajournal.data

import com.example.cinemajournal.data.API.AuthApi
import com.example.cinemajournal.data.Room.UsersDatabase
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatch
import com.example.cinemajournal.data.models.RoomModels.User
import com.example.cinemajournal.data.models.RoomModels.WatchedMovies
import com.example.cinemajournal.data.models.SignUpRequest
import com.example.cinemajournal.data.models.AuthResponse
import com.example.cinemajournal.data.models.SignInRequest
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepository @Inject constructor(database: UsersDatabase, private val authApi: AuthApi) {

    //private val database: UsersDatabase = UsersDatabase.get()
    private val userDao = database.usersDao()
    private val executor = Executors.newSingleThreadExecutor()

    suspend fun signUpUser(request: SignUpRequest): AuthResponse {
        return authApi.signUpUser(request)
    }

    suspend fun signOutUser(): AuthResponse{
        return authApi.signOutUser()
    }

    suspend fun signInUser(request: SignInRequest): AuthResponse{
        return authApi.signInUser(request)
    }

    fun saveUserToDatabase(user: User) {
        executor.execute{
            userDao.saveUser(user)
        }
    }

    suspend fun getAllUsers():List<User> {
        return userDao.getAllUsers()
    }
    fun deleteUserById(id: Int) {
        executor.execute{
            userDao.deleteUserById(id)
        }
    }

    fun deleteWatchedMovieById(id: Long) {
        /*executor.execute{
            userDao.deleteWatchedMovieById(id)
        }*/
    }
    fun deleteMovieToWatchById(id: Long) {
        /*executor.execute{
            userDao.deleteMovieToWatchById(id)
        }*/
    }

//    fun getUser(): FirebaseUser?{
//        return FirebaseAuth.getInstance().currentUser
//    }
//
//    fun getAuth(): FirebaseAuth{
//        return FirebaseAuth.getInstance()
//    }
}