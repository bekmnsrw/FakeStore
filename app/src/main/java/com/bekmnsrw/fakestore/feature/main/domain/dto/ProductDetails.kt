package com.bekmnsrw.fakestore.feature.main.domain.dto

data class ProductDetails(
    val id: Long,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating
)
