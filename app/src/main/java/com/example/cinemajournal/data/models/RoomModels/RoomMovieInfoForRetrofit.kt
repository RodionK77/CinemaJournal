package com.example.cinemajournal.data.models.RoomModels

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cinemajournal.data.models.SeasonsInfo

data class RoomMovieInfoForRetrofit (
    var id: Int,
    var name: String? = null,
    var feesWorld: Long = 0,
    var feesUsa: Long = 0,
    var budget: Int = 0,
    var posterUrl: String? = null,
    var worldPremier: String? = null,
    var russiaPremier: String? = null,
    var kpRating: Double = 0.0,
    var imdbRating: Double = 0.0,
    var movieLength: Int = 0,
    var type: String? = null,
    var typeNumber: Int = 0,
    var description: String? = null,
    var year: Int = 0,
    var alternativeName: String? = null,
    var enName: String? = null,
    var ageRating: String? = null,
    var isSeries: Boolean? = null,
    var seriesLength: String? = null,
    var totalSeriesLength: String? = null,
    var countries: List<CountriesForRetrofit>? = null,
    var genres: List<GenresForRetrofit>? = null,
    var persons: List<PersonsForRetrofit>? = null,
    var seasonsInfo: List<SeasonsInfoForRetrofit>? = null,
    var review: ReviewForRetrofit? = null
    )