package com.example.cinemajournal.data.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatch
import com.example.cinemajournal.data.models.RoomModels.User
import com.example.cinemajournal.data.models.RoomModels.WatchedMovies

@Dao
interface UsersDAO {

    @Query("DELETE FROM User WHERE id = :userId")
    fun deleteUserById(userId: String)
    @Query("SELECT * FROM User;")
    suspend fun getAllUsers(): List<User>
    @Insert(entity = User::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(item: User)

    @Insert(entity = MoviesToWatch::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveMovieToWatch(item: MoviesToWatch)
    @Query("DELETE FROM MoviesToWatch WHERE id = :movieId")
    fun deleteMovieToWatchById(movieId: Long)
    @Query("SELECT EXISTS (SELECT 1 FROM MoviesToWatch WHERE movie_id = :movieId AND user_id = :userId)")
    fun checkMovieToWatch(movieId: Long, userId: String): Boolean

    @Insert(entity = WatchedMovies::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveWatchedMovie(item: WatchedMovies)
    @Query("DELETE FROM WatchedMovies WHERE id = :movieId")
    fun deleteWatchedMovieById(movieId: Long)
    @Query("SELECT EXISTS (SELECT 1 FROM WatchedMovies WHERE movie_id = :movieId AND user_id = :userId)")
    fun checkWatchedMovie(movieId: Long, userId: String): Boolean
}