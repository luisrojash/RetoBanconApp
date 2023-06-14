package com.bancom.data.base

import android.util.Log
import com.bancom.core.model.EventResult
import com.google.gson.Gson
import retrofit2.Response


fun <T, R> Response<BaseResponse<T>>?.validateResponse(
    transform: T.() -> R,
): EventResult<R> {
    try {
        this?.let { response ->
            val errorBody: String? = response.errorBody()?.string()
            if (response.isSuccessful && errorBody.isNullOrEmpty()) {
                val responseBody: BaseResponse<T>? = response.body()
                if (responseBody?.success == true) {
                    Log.i("BaseResponse ", "responseBody:: true")
                    return responseBody.data?.let { data ->
                        Log.i("BaseResponse ", "responseBody:: data esta lleno")
                        EventResult.Success(transform.invoke(data))
                    } ?: kotlin.run {
                        Log.i("BaseResponse ", "responseBody:: data esta  nulo ")
                        if (responseBody.code in 200..299) {
                            Log.i(
                                "BaseResponse ",
                                "responseBody:: data esta  nulo pero respondio code 200"
                            )
                            try {
                                EventResult.Success(Unit as R)
                            } catch (_: Exception) {
                                Log.i(
                                    "BaseResponse ",
                                    "responseBody:: Error pero respondio code 200 "
                                )
                                EventResult.Error("Error pero respondio code 200")
                            }
                        } else {
                            Log.i(
                                "BaseResponse ",
                                "responseBody:: Error con codigo diferete de 200 "
                            )
                            EventResult.Error(responseBody.error?.errorMessage.toString())
                        }
                    }
                } else {
                    Log.i("BaseResponse ", "responseBody:: false")
                    return EventResult.Error(responseBody?.error?.errorMessage.toString())

                }
            } else {
                Log.i("BaseResponse ", "responseBody:: ERROROR")
                val responseBody = Gson().fromJson(errorBody, BaseResponse::class.java)
                Log.i(
                    "BaseResponse ",
                    "responseBody:: responseBody" + responseBody.error?.errorMessage
                )
                if (code() == 401) {

                    return EventResult.Error("Error pero respondio code 401")
                }

                Log.i("BaseResponse ", "responseBody:: responseBody   code()" + code())
                return EventResult.Error("responseBody:: responseBody   code()")

                /*return EventResult.Error(
                    returnException(code(), responseBody?.message)
                )*/
            }
        } ?: kotlin.run {
            Log.i("BaseResponse ", "responseBody:: ERROROR 2")
            return EventResult.Error("responseBody:: responseBody  2 code()")
        }
    } catch (ex: Exception) {
        Log.i("BaseResponse ", "responseBody:: ERROROR 3" + ex.localizedMessage)
        return EventResult.Error("responseBody:: responseBody  3 code()")
    }
}




