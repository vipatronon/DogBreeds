package com.victor.dogbreeds.business.dataClasses

import com.google.gson.annotations.SerializedName

data class BreedImageVO(
    @SerializedName("message") val url: String,
    @SerializedName("status") val status: String
)
