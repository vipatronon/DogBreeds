package com.victor.dogbreeds.business.models.firestore

data class FavoriteBreed(
    val masterBreed: String? = null,
    val favorite: Boolean? = null,
    val subBreed: String? = null
)