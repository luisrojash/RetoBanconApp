package com.bancom.data.datasource.users

import com.bancom.core.model.EventResult
import com.bancom.data.datasource.response.toEntity
import com.bancom.domain.model.UserDataEntity
import com.bancom.domain.users.IUsersNetworkRepository
import javax.inject.Inject

class UsersNetwork @Inject constructor(private val api: UsersApi) : IUsersNetworkRepository {

    override suspend fun getListUsers(): EventResult<List<UserDataEntity>> {
        val result = api.getListUsers()
        result.body()?.let { listData ->
            val data = listData.map { it.toEntity() }
            return EventResult.Success(data)
        } ?: kotlin.run {
            val error = result.errorBody().toString()
            return EventResult.Error("Ocurrio algun problema $error")
        }
    }

}