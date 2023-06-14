package com.bancom.core.extension


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bancom.core.model.EventResult
import kotlinx.coroutines.*


typealias DoTask<T> = suspend CoroutineScope.() -> EventResult<T>
typealias SuccessResult<T> = (success: T) -> Unit
typealias ErrorResult = ((Exception) -> Unit)?
typealias Finally = (() -> Unit)?
typealias SuccessResultCancelable<T> = (success: T, isActive: Boolean) -> Unit
typealias ErrorResultCancelable = ((Exception, isActive: Boolean) -> Unit)?

fun <T> ViewModel.launchOnIO(
    doTask: DoTask<T>,
    result: SuccessResult<T>,
    error: ErrorResult = null,
    finally: Finally = null
): Job {
    return viewModelScope.launch {
        try {
            withContext(Dispatchers.IO) {
                doTask()
            }.also {
                when (it) {
                    is EventResult.Success -> result(it.value)
                    is EventResult.Error -> error?.invoke(java.lang.Exception("Error"))
                }
            }
        } catch (ex: Exception) {
            println("Ocurrió un error")
            error?.invoke(ex)
        } finally {
            finally?.invoke()
        }
    }
}
