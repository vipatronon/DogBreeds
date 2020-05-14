package com.victor.dogbreeds.business.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BreedsModel (
    val masterBreed: String,
    val subBreed: String
    ): Parcelable
