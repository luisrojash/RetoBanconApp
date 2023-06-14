package com.bancom.myapplication.ui.listado

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bancom.core.extension.launchOnIO
import com.bancom.core.model.EventResult
import com.bancom.domain.model.UserDataEntity
import com.bancom.domain.usecase.UsersUseCase
import com.bancom.myapplication.ui.listado.result.UsersEventResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListPersonViewModel @Inject constructor(
    private val usersUseCase: UsersUseCase
) : ViewModel() {

    private val _usersLiveData: MutableLiveData<UsersEventResult> = MutableLiveData()
    val usersObserver: LiveData<UsersEventResult> get() = _usersLiveData

    init {
        initGetList()
    }

     fun initGetList() {
        launchOnIO(
            doTask = {
                usersUseCase.getListUsers()
            },
            result = {
                _usersLiveData.value = UsersEventResult.GetListUsers(it)
            },
            error = {
                _usersLiveData.value = UsersEventResult.Error(it)
            }
        )

    }
}