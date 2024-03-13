package com.example.cinemajournal.data.Room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cinemajournal.data.models.RoomModels.Countries
import com.example.cinemajournal.data.models.RoomModels.Dislikes
import com.example.cinemajournal.data.models.RoomModels.Genres
import com.example.cinemajournal.data.models.RoomModels.Likes
import com.example.cinemajournal.data.models.RoomModels.RoomMovieInfo
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatch
import com.example.cinemajournal.data.models.RoomModels.Persons
import com.example.cinemajournal.data.models.RoomModels.Review
import com.example.cinemajournal.data.models.RoomModels.SeasonsInfo
import com.example.cinemajournal.data.models.RoomModels.User
import com.example.cinemajournal.data.models.RoomModels.WatchedMovies

@Database(
    entities = [User::class, RoomMovieInfo::class, MoviesToWatch::class, WatchedMovies::class, Review::class, Countries::class, Dislikes::class, Likes::class, Genres::class, Persons::class, SeasonsInfo::class],
    version = 1,
    exportSchema = false
)
abstract class UsersDatabase : RoomDatabase() {

    abstract fun usersDao(): UsersDAO

    /*companion object {
        fun createDataBase(context: Context): UsersDatabase {
            return Room.databaseBuilder(
                context,
                UsersDatabase::class.java,
                "users.db"
            ).build()
        }
    }*/

//    companion object {
//        private var INSTANCE: UsersDatabase? = null
//
//        fun initialize(context: Context) {
//            if (INSTANCE == null) {
//                INSTANCE = Room.databaseBuilder(
//                    context.applicationContext,
//                    UsersDatabase::class.java,
//                    "users_database"
//                ).build()
//            }
//        }
//
//        fun get(): UsersDatabase {
//            return INSTANCE ?: throw IllegalStateException("UsersRepository must be initialized")
//        }
//    }
}