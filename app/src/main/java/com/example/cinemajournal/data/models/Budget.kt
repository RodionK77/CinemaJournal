package com.example.example

import com.google.gson.annotations.SerializedName


data class Budget (

  @SerializedName("value"    ) var value    : Int?    = null,
  @SerializedName("currency" ) var currency : String? = null

)