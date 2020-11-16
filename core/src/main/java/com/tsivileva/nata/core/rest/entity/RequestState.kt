package com.tsivileva.nata.core.rest.entity

sealed class RequestState<T> {
    class Success<T>(val data: T) : RequestState<T>()
    class Error<T>(val message: String) : RequestState<T>()
    class Loading<T> : RequestState<T>()
}