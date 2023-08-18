package com.bekmnsrw.fakestore.feature.main.domain.dto

data class ProductMain(
    val id: Long,
    val title: String,
    val fullPrice: Double,
    val discountPrice: Double,
    val image: String,
    val rate: Double,
    val count: Long
)
