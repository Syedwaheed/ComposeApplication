package com.newapp.composeapplicationstart.domain.repository

import com.newapp.composeapplicationstart.domain.model.response.MovieResponse
import com.newapp.composeapplicationstart.data.datasource.remote.TMBDApiInterface
import com.newapp.composeapplicationstart.data.utils.DataError
import com.newapp.composeapplicationstart.data.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface MovieRepository {
   fun getPopularMovies(): Flow<Result<List<MovieResponse?>?, DataError.Network>>
}
