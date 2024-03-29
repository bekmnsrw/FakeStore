package com.bekmnsrw.fakestore.feature.main.data

import com.bekmnsrw.fakestore.core.network.ProductApi
import com.bekmnsrw.fakestore.feature.main.data.mapper.toProductDetails
import com.bekmnsrw.fakestore.feature.main.data.mapper.toProductMainList
import com.bekmnsrw.fakestore.feature.main.domain.ProductRepository
import com.bekmnsrw.fakestore.feature.main.domain.dto.ProductDetails
import com.bekmnsrw.fakestore.feature.main.domain.dto.ProductMain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApi
) : ProductRepository {

    override suspend fun getProductsPaged(
        limit: Int,
        skip: Int
    ): List<ProductMain> = productApi
        .getProductsPaged(
            limit = limit,
            skip = skip
        )
        .products
        .toProductMainList()

    override suspend fun getProductById(
        id: Long
    ): Flow<ProductDetails> = flow {
        emit(
            productApi
                .getProductById(id)
                .toProductDetails()
        )
    }
}
