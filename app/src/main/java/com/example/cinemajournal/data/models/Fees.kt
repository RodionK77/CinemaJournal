package com.example.example

import com.google.gson.annotations.SerializedName


data class Fees (

  @SerializedName("world" ) var world : World? = World(),
  @SerializedName("usa"   ) var usa   : Usa?   = Usa()

)