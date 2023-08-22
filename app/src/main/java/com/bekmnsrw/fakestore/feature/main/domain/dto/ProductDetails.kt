package com.bekmnsrw.fakestore.feature.main.domain.dto

data class ProductDetails(
    val id: Long,
    val title: String,
    val description: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Long,
    val brand: String,
    val category: String,
    val thumbnail: String,
    val images: List<String>
)
