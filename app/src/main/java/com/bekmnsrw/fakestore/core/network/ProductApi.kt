package com.bekmnsrw.fakestore.core.network

import com.bekmnsrw.fakestore.feature.main.data.response.Product
import com.bekmnsrw.fakestore.feature.main.data.response.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {

    @GET("products")
    suspend fun getProductsPaged(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): ProductResponse

    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") id: Long
    ): Product
}
