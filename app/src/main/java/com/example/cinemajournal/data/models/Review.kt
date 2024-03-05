package com.example.cinemajournal.data.models

import com.example.example.MovieInfo

data class Review(
    var movieInfo: MovieInfo,
    val rating: Int = 0,
    var likes: ArrayList<String> = arrayListOf(),
    var dislikes: ArrayList<String> = arrayListOf(),
    var notes: String = ""
)
