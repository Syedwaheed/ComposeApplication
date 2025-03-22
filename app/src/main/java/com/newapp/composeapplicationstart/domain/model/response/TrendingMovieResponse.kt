package com.newapp.composeapplicationstart.domain.model.response

data class TrendingMovieResponse(
    val id: Int,
    val name: String,
    val posterPath: String,
    val rating: Double?
)