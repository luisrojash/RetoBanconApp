package com.bancom.domain.usecase

import com.bancom.core.model.EventResult
import com.bancom.domain.model.UserDataEntity
import com.bancom.domain.repository.users.IUsersNetworkRepository
import javax.inject.Inject

class UsersUseCase @Inject constructor(
    private val remote: IUsersNetworkRepository,
){
    suspend fun getListUsers() : EventResult<MutableList<UserDataEntity>> {
        return remote.getListUsers()
    }
}