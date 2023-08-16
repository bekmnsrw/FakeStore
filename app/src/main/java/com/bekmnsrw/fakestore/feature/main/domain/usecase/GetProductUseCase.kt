package com.bekmnsrw.fakestore.feature.main.domain.usecase

import com.bekmnsrw.fakestore.feature.main.domain.ProductRepository
import com.bekmnsrw.fakestore.feature.main.domain.dto.ProductDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

    suspend operator fun invoke(id: Long): Flow<ProductDetails> = productRepository.getProduct(id)
}
