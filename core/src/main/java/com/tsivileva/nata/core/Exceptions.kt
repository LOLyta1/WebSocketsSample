package com.tsivileva.nata.core

abstract class SocketException(
    override val message: String
) : Throwable(message)

class EmptyParameterException(
    val className:String
):SocketException("Parameters was not set! $className")

class HttpRequestException():
    SocketException("Http requests are not allowed here")
