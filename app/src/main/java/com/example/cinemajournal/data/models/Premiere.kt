package com.example.example

import com.google.gson.annotations.SerializedName


data class Premiere (

  @SerializedName("world"  ) var world  : String? = null,
  @SerializedName("russia" ) var russia : String? = null

)