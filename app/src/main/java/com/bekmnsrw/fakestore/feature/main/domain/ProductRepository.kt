package com.bekmnsrw.fakestore.feature.main.domain

import com.bekmnsrw.fakestore.feature.main.domain.dto.ProductDetails
import com.bekmnsrw.fakestore.feature.main.domain.dto.ProductMain
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun getProductsPaged(
        limit: Int,
        skip: Int
    ): List<ProductMain>

    suspend fun getProductById(
        id: Long
    ): Flow<ProductDetails>
}
