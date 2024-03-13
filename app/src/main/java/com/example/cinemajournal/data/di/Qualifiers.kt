package com.example.cinemajournal.data.di

import javax.inject.Qualifier

class Qualifiers {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class Auth

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class Movies

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MoviesDB
}