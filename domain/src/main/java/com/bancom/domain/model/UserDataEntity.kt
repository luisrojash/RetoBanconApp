package com.bancom.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserDataEntity(
    val name: String,
    val userName: String,
    val email: String
): Parcelable

