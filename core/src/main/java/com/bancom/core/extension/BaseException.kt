package com.bancom.core.extension

import java.io.IOException

sealed class BaseException(
    errorMessage: String
) : IOException(errorMessage) {

    data class GeneralException(
        var errorMessage: String = "Ocurrió un error inesperado",
    ) : BaseException(errorMessage)

    data class AirplaneException(
        var errorMessage: String
    ) : BaseException(errorMessage)

    data class NetworkException(
        var errorMessage: String
    ) : BaseException(errorMessage)

    data class BadRequestException(
        var errorMessage: String
    ) : BaseException(errorMessage)

    data class UnAuthorizeException(
        var errorMessage: String = "Ocurrió un error inesperado"
    ) : BaseException(errorMessage)

    data class PushNotificationTokenException(
        var errorMessage: String = "Ocurrió un error inesperado"
    ) : BaseException(errorMessage)
}
