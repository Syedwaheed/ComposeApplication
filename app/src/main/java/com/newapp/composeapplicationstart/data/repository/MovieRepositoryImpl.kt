package com.newapp.composeapplicationstart.data.repository

import android.util.Log
import com.newapp.composeapplicationstart.data.datasource.remote.TMBDApiInterface
import com.newapp.composeapplicationstart.data.mapper.MovieMapper
import com.newapp.composeapplicationstart.data.utils.mapToResult
import com.newapp.composeapplicationstart.domain.model.response.MovieResponse
import com.newapp.composeapplicationstart.domain.repository.MovieRepository
import com.newapp.composeapplicationstart.data.utils.DataError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.newapp.composeapplicationstart.data.utils.Result
import com.newapp.composeapplicationstart.data.utils.mapIfNotNull
import com.newapp.composeapplicationstart.data.utils.mapIfNotNullList
import com.newapp.composeapplicationstart.data.utils.safeApiCall
import javax.inject.Inject

const val TAG = "*APIKEY"

class MovieRepositoryImpl @Inject constructor(
    private val apiService: TMBDApiInterface,
    private val movieMapper: MovieMapper,
    private val apiKey: String
) : MovieRepository {

    override fun getPopularMovies():
            Flow<Result<List<MovieResponse?>?, DataError.Network>> {
        return safeApiCall(
            apiCall = { apiService.getPopularMovie(apiKey)?.results },
            mapper = {
                it.mapIfNotNullList { movieResponse ->
                    movieMapper.mapFromDTO(movieResponse)
                }
            }
        )
    }
}
//        safeApiCall {
//            val response = apiService.getPopularMovie(apiKey)?.results
//            response?.map { movieMapper.mapFromDTO(it) }
//        }

//        return flow {
//            Log.d(TAG, "apiKey: $apiKey ")
//            val response = apiService.getPopularMovie(apiKey)
//            val mapper = response?.results?.map { movieMapper.mapFromDTO(it) }
//            emit(mapper)
//        }.mapToResult()
//    }
/*= flow {
    try {

    }catch (e: Exception){
        emit(Result.failure(e))
    }
}*/
