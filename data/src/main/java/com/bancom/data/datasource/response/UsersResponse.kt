package com.bancom.data.datasource.response

import com.bancom.domain.model.UserDataEntity
import com.google.gson.annotations.SerializedName


data class UserListResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("username")
    val userName: String,
    @SerializedName("email")
    val email: String
)


fun UserListResponse.toEntity() = UserDataEntity(
    name = name,
    userName = userName,
    email = email
)