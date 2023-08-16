package com.bekmnsrw.fakestore.core.network

import com.bekmnsrw.fakestore.feature.main.data.response.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {

    @GET("products")
    suspend fun getAllProducts(): List<ProductResponse>

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Long): ProductResponse
}
