package com.tsivileva.nata.network.rest

import com.tsivileva.nata.core.model.dto.OrderSnapshot
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApi {
    @GET("api/v3/depth")
    suspend fun getOrders(
        @Query("symbol") currency: String,
        @Query("limit") limit: Int
    ): OrderSnapshot
}