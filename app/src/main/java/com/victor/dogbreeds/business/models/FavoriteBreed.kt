package com.victor.dogbreeds.business.models

data class FavoriteBreed(
    val masterBreed: String? = null,
    val favorite: Boolean? = null,
    val subBreed: String? = null
)