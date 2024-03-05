package com.example.cinemajournal.data.models

data class SignUpRequest (
    var username: String,
    var email: String,
    var password: String,
    var role: Int
)