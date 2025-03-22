package com.newapp.composeapplicationstart.domain.repository

import com.newapp.composeapplicationstart.data.utils.DataError
import com.newapp.composeapplicationstart.data.utils.Result
import com.newapp.composeapplicationstart.domain.model.response.MovieResponse
import com.newapp.composeapplicationstart.domain.model.response.TrendingMovieResponse
import kotlinx.coroutines.flow.Flow

interface TrendingMovieRepo {
    fun getTrendingMovie() : Flow<Result<List<TrendingMovieResponse?>,DataError.Network>>
}