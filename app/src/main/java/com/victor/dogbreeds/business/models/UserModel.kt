package com.victor.dogbreeds.business.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    val id: String,
    val fullname: String,
    val birthdate: String,
    val email: String
): Parcelable