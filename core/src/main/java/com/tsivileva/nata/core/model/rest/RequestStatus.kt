package com.tsivileva.nata.core.model.rest

sealed class RequestStatus {
    object Success : RequestStatus()
    class Error(val message: String) : RequestStatus()
    object Loading : RequestStatus()
}