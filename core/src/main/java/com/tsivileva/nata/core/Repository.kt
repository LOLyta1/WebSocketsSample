package com.tsivileva.nata.core

interface Repository<T> {
    suspend fun load(): T?
    suspend fun unsubscribe()
    suspend fun subscribe()
    suspend fun cancel()
    suspend fun close()
}
