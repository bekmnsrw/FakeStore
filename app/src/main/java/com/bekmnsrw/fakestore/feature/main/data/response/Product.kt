package com.bekmnsrw.fakestore.feature.main.data.response

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id") val id: Long = 0L,
    @SerializedName("title") val title: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("price") val price: Double = 0.0,
    @SerializedName("discountPercentage") val discountPercentage: Double = 0.0,
    @SerializedName("rating") val rating: Double = 0.0,
    @SerializedName("stock") val stock: Long = 0L,
    @SerializedName("brand") val brand: String = "",
    @SerializedName("category") val category: String = "",
    @SerializedName("thumbnail") val thumbnail: String = "",
    @SerializedName("images") val images: List<String> = listOf()
)
