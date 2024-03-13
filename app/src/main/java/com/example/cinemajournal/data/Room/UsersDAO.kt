package com.example.cinemajournal.data.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cinemajournal.data.models.RoomModels.Countries
import com.example.cinemajournal.data.models.RoomModels.Dislikes
import com.example.cinemajournal.data.models.RoomModels.Genres
import com.example.cinemajournal.data.models.RoomModels.Likes
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatch
import com.example.cinemajournal.data.models.RoomModels.Persons
import com.example.cinemajournal.data.models.RoomModels.Review
import com.example.cinemajournal.data.models.RoomModels.RoomMovieInfo
import com.example.cinemajournal.data.models.RoomModels.SeasonsInfo
import com.example.cinemajournal.data.models.RoomModels.User
import com.example.cinemajournal.data.models.RoomModels.WatchedMovies

@Dao
interface UsersDAO {

    @Query("DELETE FROM User WHERE id = :userId")
    fun deleteUserById(userId: Int)

    @Query("DELETE FROM MoviesToWatch WHERE user_id = :userId AND movie_id = :movieId")
    fun deleteMoviesToWatchById(userId: Int, movieId: Int)

    @Query("SELECT * FROM User")
    suspend fun getAllUsers(): List<User>
    @Insert(entity = User::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(item: User)

    @Insert(entity = RoomMovieInfo::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveMovieToLocalDB(item: RoomMovieInfo)

    @Query("SELECT * FROM RoomMovieInfo WHERE id = :movieId")
    suspend fun getMovieByIdFromLocalDB(movieId: Int): RoomMovieInfo?

    @Query("SELECT * FROM RoomMovieInfo")
    suspend fun getAllMoviesFromLocalDB(): List<RoomMovieInfo>

    @Query("DELETE FROM RoomMovieInfo WHERE id = :movieId")
    fun deleteMovieByIdFromLocalDB(movieId: Int)

    @Query("DELETE FROM RoomMovieInfo")
    fun deleteAllMoviesFromLocalDB()

    @Insert(entity = Countries::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveCountriesToLocalDB(item: Countries)

    @Query("SELECT * FROM Countries WHERE content_id = :movieId")
    suspend fun getCountriesByIdFromLocalDB(movieId: Int): Countries?

    @Insert(entity = Genres::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveGenresToLocalDB(item: Genres)

    @Query("SELECT * FROM Genres WHERE content_id = :movieId")
    suspend fun getGenresByIdFromLocalDB(movieId: Int): Genres?

    @Insert(entity = Persons::class, onConflict = OnConflictStrategy.REPLACE)
    fun savePersonsToLocalDB(item: Persons)

    @Query("SELECT * FROM Persons WHERE content_id = :movieId")
    suspend fun getPersonsByIdFromLocalDB(movieId: Int): Persons?

    @Insert(entity = SeasonsInfo::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveSeasonsInfoToLocalDB(item: SeasonsInfo)

    @Query("SELECT * FROM SeasonsInfo WHERE content_id = :movieId")
    suspend fun getSeasonsInfoByIdFromLocalDB(movieId: Int): SeasonsInfo?

    @Insert(entity = MoviesToWatch::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveMovieToWatchToLocalDB(item: MoviesToWatch)

    @Query("SELECT * FROM MoviesToWatch WHERE user_id = :userId")
    suspend fun getAllMoviesToWatchById(userId: Int): List<MoviesToWatch>

    @Query("SELECT EXISTS (SELECT 1 FROM MoviesToWatch WHERE movie_id = :movieId AND user_id = :userId)")
    fun checkMovieToWatch(movieId: Int, userId: Int): Boolean

    @Query("DELETE FROM MoviesToWatch")
    fun deleteAllMoviesToWatchFromLocalDB()

    @Insert(entity = WatchedMovies::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveWatchedMovieToLocalDB(item: WatchedMovies)

    @Query("SELECT * FROM WatchedMovies WHERE user_id = :userId")
    suspend fun getAllWatchedMoviesById(userId: Int): List<WatchedMovies>

    @Query("DELETE FROM WatchedMovies WHERE user_id = :userId AND movie_id = :movieId")
    fun deleteWatchedMoviesById(userId: Int, movieId: Int)

    @Query("SELECT EXISTS (SELECT 1 FROM WatchedMovies WHERE movie_id = :movieId AND user_id = :userId)")
    fun checkWatchedMovie(movieId: Int, userId: Int): Boolean

    @Query("DELETE FROM WatchedMovies")
    fun deleteAllWatchedMoviesFromLocalDB()

    @Insert(entity = Review::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveReviewToLocalDB(item: Review)

    @Query("SELECT * FROM Review WHERE user_id = :userId")
    suspend fun getAllReviewsById(userId: Int): List<Review>

    @Query("DELETE FROM Review WHERE user_id = :userId AND movie_id = :movieId")
    fun deleteReviewById(userId: Int, movieId: Int)

    @Query("DELETE FROM Review")
    fun deleteAllReviewsFromLocalDB()

    @Insert(entity = Likes::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveLikesToLocalDB(item: Likes)

    @Query("SELECT * FROM Likes WHERE user_id = :userId")
    suspend fun getAllLikesById(userId: Int): List<Likes>

    @Query("DELETE FROM Likes WHERE likesId = :likesId")
    fun deleteLikesById(likesId: Int)

    @Query("DELETE FROM Likes")
    fun deleteAllLikesFromLocalDB()

    @Insert(entity = Dislikes::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveDislikesToLocalDB(item: Dislikes)

    @Query("SELECT * FROM Dislikes WHERE user_id = :userId")
    suspend fun getAllDislikesById(userId: Int): List<Dislikes>

    @Query("DELETE FROM Dislikes WHERE dislikesId = :dislikesId")
    fun deleteDislikesById(dislikesId: Int)

    @Query("DELETE FROM Dislikes")
    fun deleteAllDislikesFromLocalDB()

}