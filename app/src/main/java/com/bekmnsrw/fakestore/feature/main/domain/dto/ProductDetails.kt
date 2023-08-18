package com.bekmnsrw.fakestore.feature.main.domain.dto

data class ProductDetails(
    val id: Long,
    val title: String,
    val fullPrice: Double,
    val discountPrice: Double,
    val description: String,
    val category: String,
    val image: String,
    val rate: Double,
    val count: Long,
    val available: Long,
    val orders: Long
)
