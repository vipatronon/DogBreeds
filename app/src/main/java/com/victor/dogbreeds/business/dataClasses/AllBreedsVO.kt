package com.victor.dogbreeds.business.dataClasses

import com.google.gson.annotations.SerializedName

data class AllBreedsVO(
    @SerializedName("message") val message: List<BreedsVO>,
    @SerializedName("status") val status: String
)