package com.example.example

import com.google.gson.annotations.SerializedName


data class MovieListResponse (

  @SerializedName("docs"  ) var movieInfo  : ArrayList<MovieInfo> = arrayListOf(),
  @SerializedName("total" ) var total : Int?            = null,
  @SerializedName("limit" ) var limit : Int?            = null,
  @SerializedName("page"  ) var page  : Int?            = null,
  @SerializedName("pages" ) var pages : Int?            = null

)