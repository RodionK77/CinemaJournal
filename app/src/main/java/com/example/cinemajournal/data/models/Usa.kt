package com.example.example

import com.google.gson.annotations.SerializedName


data class Usa (

  @SerializedName("value"    ) var value    : Long?    = null,
  @SerializedName("currency" ) var currency : String? = null

)