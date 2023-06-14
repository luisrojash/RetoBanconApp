package com.bancom.core.model

data class ErrorResponse(
    val status: String?,
    val type: String?,
    val title: String?,
    val detail: String?
)