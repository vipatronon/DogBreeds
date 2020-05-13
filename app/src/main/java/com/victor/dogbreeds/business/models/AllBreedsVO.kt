package com.victor.dogbreeds.business.models

import com.google.gson.annotations.SerializedName

data class AllBreedsVO (
    @SerializedName("message") val message : BreedsVO,
    @SerializedName("status") val status : String
)