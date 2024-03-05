package com.example.example

import com.google.gson.annotations.SerializedName


data class Rating (

  @SerializedName("kp"                 ) var kp                 : Double? = null,
  @SerializedName("imdb"               ) var imdb               : Double?    = null,
  @SerializedName("filmCritics"        ) var filmCritics        : Double? = null,
  @SerializedName("russianFilmCritics" ) var russianFilmCritics : Double?    = null,
  @SerializedName("await"              ) var await              : String? = null

)