package com.bekmnsrw.fakestore.core.network

import com.bekmnsrw.fakestore.feature.main.data.response.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApi {

    @GET("products")
    suspend fun getAllProducts(
        @Query("limit") limit: Long
    ): ProductResponse
}
