package com.example.cinemajournal.data.models

import com.google.gson.annotations.SerializedName

data class AuthResponse (

    @SerializedName("message" ) var message : String? = null,
    var id: Int? = null,
    var username: String? = null,
    var email: String? = null,
    var role: Int? = null
) {
}