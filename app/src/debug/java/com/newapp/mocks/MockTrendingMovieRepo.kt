package com.newapp.mocks

import com.newapp.composeapplicationstart.data.utils.DataError
import com.newapp.composeapplicationstart.data.utils.Result
import com.newapp.composeapplicationstart.domain.model.response.TrendingMovieResponse
import com.newapp.composeapplicationstart.domain.repository.TrendingMovieRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockTrendingMovieRepo : TrendingMovieRepo {
    override fun getTrendingMovie(): Flow<Result<List<TrendingMovieResponse?>, DataError.Network>> {
        return flow{
            emit(Result.Success(listOf(
                TrendingMovieResponse(id =1,
                    name ="Mock Movie 1",
                    posterPath = "url1",
                    rating = 1.0
                    ),
                TrendingMovieResponse(id =2,
                    name ="Mock Movie 2",
                    posterPath = "url2",
                    rating = 2.0
                ),
                TrendingMovieResponse(id =3,
                    name ="Mock Movie 3",
                    posterPath = "url3",
                    rating = 3.0
                )
            )))
        }
    }
}