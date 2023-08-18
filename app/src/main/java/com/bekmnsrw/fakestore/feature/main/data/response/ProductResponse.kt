package com.bekmnsrw.fakestore.feature.main.data.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("id") val id: Long = 0L,
    @SerializedName("title") val title: String = "",
    @SerializedName("fullPrice") val fullPrice: Double = 0.0,
    @SerializedName("discountPrice") val discountPrice: Double = 0.0,
    @SerializedName("description") val description: String = "",
    @SerializedName("category") val category: String = "",
    @SerializedName("image") val image: String = "",
    @SerializedName("rate") val rate: Double = 0.0,
    @SerializedName("count") val count: Long = 0L,
    @SerializedName("available") val available: Long = 0L,
    @SerializedName("orders") val orders: Long = 0L
)
