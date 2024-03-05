package com.example.example

import com.example.cinemajournal.data.models.SeasonsInfo
import com.google.gson.annotations.SerializedName


data class MovieInfo (

  @SerializedName("fees"            ) var fees            : Fees?                = Fees(),
  @SerializedName("rating"          ) var rating          : Rating?              = Rating(),
  @SerializedName("movieLength"     ) var movieLength     : Int?                 = null,
  @SerializedName("id"              ) var id              : Int?                 = null,
  @SerializedName("type"            ) var type            : String?              = null,
  @SerializedName("typeNumber"      ) var typeNumber      : Int?                 = null,
  @SerializedName("name"            ) var name            : String?              = null,
  @SerializedName("description"     ) var description     : String?              = null,
  @SerializedName("premiere"        ) var premiere        : Premiere?            = Premiere(),
  @SerializedName("year"            ) var year            : Int?                 = null,
  @SerializedName("budget"          ) var budget          : Budget?              = Budget(),
  @SerializedName("poster"          ) var poster          : Poster?              = Poster(),
  @SerializedName("genres"          ) var genres          : ArrayList<Genres>    = arrayListOf(),
  @SerializedName("countries"       ) var countries       : ArrayList<Countries> = arrayListOf(),
  @SerializedName("persons"         ) var persons         : ArrayList<Persons>   = arrayListOf(),
  @SerializedName("alternativeName" ) var alternativeName : String?              = null,
  @SerializedName("enName"          ) var enName          : String?              = null,
  @SerializedName("ageRating"       ) var ageRating       : Int?                 = null,
  @SerializedName("isSeries"        ) var isSeries        : Boolean?             = null,
  @SerializedName("seriesLength"    ) var seriesLength    : String?              = null,
  @SerializedName("totalSeriesLength") var totalSeriesLength   : String?         = null,
  @SerializedName("seasonsInfo"     ) var seasonsInfo     : ArrayList<SeasonsInfo> = arrayListOf(),

  )