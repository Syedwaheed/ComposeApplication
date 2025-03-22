package com.newapp.composeapplicationstart.data.datasource.remote

import com.newapp.composeapplicationstart.data.datasource.model.response.MovieResponseDTO
import com.newapp.composeapplicationstart.data.datasource.model.response.TrendingMovieResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface TMBDApiInterface {
    @GET("movie/popular")
    suspend fun getPopularMovie(@Query("api_key") apiKey: String): MovieResponseDTO?

    @GET("trending/all/day")
    suspend fun getTrendingMovies(
        @Query("language") language: String = "en-US"
    ) : TrendingMovieResponseDTO
}