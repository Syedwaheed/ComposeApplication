package com.newapp.composeapplicationstart.presentation.viewmodel.abs

import com.newapp.composeapplicationstart.domain.model.response.TrendingMovieResponse
import com.newapp.composeapplicationstart.presentation.utils.MovieUiState
import kotlinx.coroutines.flow.StateFlow

interface ITrendingViewModel {
    val trendingMovies: StateFlow<MovieUiState<List<TrendingMovieResponse?>>>
}