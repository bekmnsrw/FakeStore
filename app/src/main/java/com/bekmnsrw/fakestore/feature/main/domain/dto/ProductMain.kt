package com.bekmnsrw.fakestore.feature.main.domain.dto

data class ProductMain(
    val id: Long,
    val title: String,
    val price: Double,
    val discountPercentage: Double,
    val thumbnail: String,
    val rating: Double,
    val stock: Long
)
