package com.bancom.myapplication.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class LoginViewModel@Inject constructor(
) :ViewModel() {


    private val _email = MutableLiveData<String>()
    var email : LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    var password : LiveData<String> = _password

    private val _loginEnable =MutableLiveData<Boolean>()
    val loginEnable = _loginEnable

    private val _isLoading =MutableLiveData<Boolean>()
    val isLoading = _isLoading
    private val _isStatusStartActivity =MutableLiveData<Boolean>()
    val isStatusStartActivity = _isStatusStartActivity

    fun onLoginChanged(email: String, password: String) {
        _email.value=email
        _password.value=password
        _loginEnable.value = isValidEmail(email) && isValidPassword(password)
    }
    private fun isValidPassword(password: String): Boolean = password.length > 6

    private fun isValidEmail(email: String): Boolean  = Patterns.EMAIL_ADDRESS.matcher(email).matches()

     suspend fun onLoginSelected() {
         _isLoading.value = true
         delay(4000)
         _isLoading.value = false
         _isStatusStartActivity.value= true
    }
}