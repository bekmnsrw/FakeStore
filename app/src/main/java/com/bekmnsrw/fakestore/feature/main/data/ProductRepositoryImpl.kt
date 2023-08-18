package com.bekmnsrw.fakestore.feature.main.data

import com.bekmnsrw.fakestore.feature.main.data.mapper.toProductMainList
import com.bekmnsrw.fakestore.feature.main.data.mapper.toProductResponse
import com.bekmnsrw.fakestore.feature.main.data.response.ProductResponse
import com.bekmnsrw.fakestore.feature.main.domain.ProductRepository
import com.bekmnsrw.fakestore.feature.main.domain.dto.ProductMain
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : ProductRepository {

    override suspend fun getAllProducts(): Flow<List<ProductMain>> {
        val products = mutableListOf<ProductResponse>()

        firebaseFirestore
            .collection("products")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    document.data.values.forEach { response ->
                        if (response is java.util.HashMap<*, *>) products.add(response.toProductResponse())
                    }
                }
            }
            .await()

        return flowOf(products.toProductMainList())
    }
}
