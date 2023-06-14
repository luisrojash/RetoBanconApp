package com.bancom.core.model

/*
una clase de  enumeración llamada ErrorRetrofitType
 que contiene cuatro constantes llamadas
 AIRPLANE_ACTIVE, NETWORK_EXCEPTION,
  UNAUTHORIZED y EXCEPTION.
  En el cual estaremos haciendo el uso
  para poder definir sus errores en la clase CustomCallAdapterFactory
 */

enum class ErrorRetrofitType{
    AIRPLANE_ACTIVE,
    NETWORK_EXCEPTION,
    UNAUTHORIZED,
    EXCEPTION
}