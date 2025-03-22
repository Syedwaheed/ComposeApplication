package com.newapp.composeapplicationstart.domain.model.response

data class MovieResponse(
    val id: Int?,
    val title: String?,
    val overview: String?,
    val posterPath: String?
)