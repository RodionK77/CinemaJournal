package com.example.cinemajournal.data.di

import android.app.Application
import androidx.room.Room
import com.example.cinemajournal.data.Room.UsersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideUsersDb(app: Application): UsersDatabase{
        return Room.databaseBuilder(
            app,
            UsersDatabase::class.java,
            "users.db"
        ).build()
    }
}