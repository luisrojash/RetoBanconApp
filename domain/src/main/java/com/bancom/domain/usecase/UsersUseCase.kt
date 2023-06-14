package com.bancom.domain.usecase

import com.bancom.core.model.EventResult
import com.bancom.domain.model.UserDataEntity
import com.bancom.domain.users.IUsersNetworkRepository
import javax.inject.Inject

class UsersUseCase @Inject constructor(
    private val remote: IUsersNetworkRepository,
){
    suspend fun getListUsers() : EventResult<List<UserDataEntity>> {
        return remote.getListUsers()
    }
}