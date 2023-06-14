package com.bancom.core.model


/*Este es una clase sellada llamada EventResult que tiene dos subclases anidadas: Success y Failure*/
sealed class EventResult<out T> {
    /*
    La clase Success tiene un tipo genérico T que representa el tipo de datos que se espera recibir como resultado de un evento.
     La propiedad data es un objeto que almacena el resultado exitoso
     */
    data class Success<T>(val value: T) : EventResult<T>()
    /*
    La clase Failure no tiene un tipo genérico y representa un resultado de evento fallido.
     Tiene tres propiedades: type, que es un objeto de enumeración que describe el tipo de error
      (como "no se pudo conectar al servidor"),
      responseError, que es un objeto que contiene información detallada sobre el error
       (como un mensaje de error del servidor), y data, que es un objeto opcional
        que se puede incluir para proporcionar más información sobre el error. En este caso, el tipo de data es String.
    */
    data class Error<T>(val exception: String) : EventResult<T>()

}