package com.bancom.data.datasource.users

import com.bancom.data.datasource.response.UserListResponse
import retrofit2.Response
import retrofit2.http.GET

interface UsersApi {

    @GET("users")
    suspend fun getListUsers(): Response<List<UserListResponse>>

}