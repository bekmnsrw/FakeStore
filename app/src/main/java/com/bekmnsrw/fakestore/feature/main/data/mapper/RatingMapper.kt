package com.bekmnsrw.fakestore.feature.main.data.mapper

import com.bekmnsrw.fakestore.feature.main.data.response.RatingResponse
import com.bekmnsrw.fakestore.feature.main.domain.dto.Rating

fun RatingResponse.toRating(): Rating = Rating(
    rate = rate,
    count = count
)
