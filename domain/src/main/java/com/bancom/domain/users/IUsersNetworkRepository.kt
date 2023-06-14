package com.bancom.domain.repository.users

import com.bancom.core.model.EventResult
import com.bancom.domain.model.UserDataEntity

interface IUsersNetworkRepository {

    suspend fun getListUsers(): EventResult<MutableList<UserDataEntity>>
}