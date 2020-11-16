package com.tsivileva.nata.network.rest

import retrofit2.http.GET
import retrofit2.http.Query
import com.tsivileva.nata.core.model.dto.OrderSnapshot

interface RestApi {
    @GET("api/v3/depth")
    suspend fun getOrders(
        @Query("symbol") currency: String,
        @Query("limit") limit: Int
    ): OrderSnapshot
}