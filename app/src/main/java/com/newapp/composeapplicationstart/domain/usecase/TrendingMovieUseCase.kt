package com.newapp.composeapplicationstart.domain.usecase

import android.provider.ContactsContract.Data
import com.newapp.composeapplicationstart.data.utils.DataError
import com.newapp.composeapplicationstart.data.utils.Result
import com.newapp.composeapplicationstart.domain.model.response.TrendingMovieResponse
import com.newapp.composeapplicationstart.domain.repository.TrendingMovieRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrendingMovieUseCase @Inject constructor(
    private val trendingMovieRepo: TrendingMovieRepo
){
    operator fun  invoke(): Flow<Result<List<TrendingMovieResponse?>, DataError.Network>>{
        return trendingMovieRepo.getTrendingMovie()
    }
}