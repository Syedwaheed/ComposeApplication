package com.newapp.composeapplicationstart.data.repository

import com.newapp.composeapplicationstart.data.datasource.model.response.TrendingMovieResponseDTO
import com.newapp.composeapplicationstart.data.datasource.remote.TMBDApiInterface
import com.newapp.composeapplicationstart.data.mapper.MovieMapper
import com.newapp.composeapplicationstart.data.utils.DataError
import com.newapp.composeapplicationstart.data.utils.Result
import com.newapp.composeapplicationstart.data.utils.mapIfNotNull
import com.newapp.composeapplicationstart.data.utils.mapIfNotNullList
import com.newapp.composeapplicationstart.data.utils.safeApiCall
import com.newapp.composeapplicationstart.domain.model.response.MovieResponse
import com.newapp.composeapplicationstart.domain.model.response.TrendingMovieResponse
import com.newapp.composeapplicationstart.domain.repository.TrendingMovieRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrendingMovieRepoImpl @Inject constructor(
    private val apiService:TMBDApiInterface,
    private val movieMapper: MovieMapper,
): TrendingMovieRepo {
    override fun getTrendingMovie(): Flow<Result<List<TrendingMovieResponse?>, DataError.Network>> {
        return safeApiCall(
            apiCall = {apiService.getTrendingMovies().results},
            mapper = { result ->
                result.mapIfNotNullList {
                    it?.let {
                        movieMapper.mapTrendingResponseDTO(it)
                    }
                }
            }
        )
    }
}