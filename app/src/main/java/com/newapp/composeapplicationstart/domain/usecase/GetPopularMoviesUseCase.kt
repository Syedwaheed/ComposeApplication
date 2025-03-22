package com.newapp.composeapplicationstart.domain.usecase

import com.newapp.composeapplicationstart.domain.model.response.MovieResponse
import com.newapp.composeapplicationstart.domain.repository.MovieRepository
import com.newapp.composeapplicationstart.data.utils.DataError
import com.newapp.composeapplicationstart.data.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(): Flow<Result<List<MovieResponse?>?, DataError.Network>>{
        return movieRepository.getPopularMovies()
    }
}