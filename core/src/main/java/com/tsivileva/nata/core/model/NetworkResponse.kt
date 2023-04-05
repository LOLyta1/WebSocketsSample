package com.tsivileva.nata.core.model

sealed class NetworkResponse<T> {
    class Successful<T>(val data: T) : NetworkResponse<T>()
    class Error<T>(val message: String) : NetworkResponse<T>()
    class Loading<T>() : NetworkResponse<T>()
}