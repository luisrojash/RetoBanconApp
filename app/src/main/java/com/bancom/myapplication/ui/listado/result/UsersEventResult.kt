package com.bancom.myapplication.ui.listado.result

import com.bancom.domain.model.UserDataEntity

sealed class UsersEventResult {

    data class GetListUsers(val usersList: List<UserDataEntity>) : UsersEventResult()
    data class Error(val ex: Exception) : UsersEventResult()
}