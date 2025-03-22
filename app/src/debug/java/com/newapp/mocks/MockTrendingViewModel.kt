package com.newapp.mocks

import com.newapp.composeapplicationstart.data.utils.Result
import com.newapp.composeapplicationstart.domain.model.response.TrendingMovieResponse
import com.newapp.composeapplicationstart.presentation.utils.MovieUiState
import com.newapp.composeapplicationstart.presentation.utils.asUiText
import com.newapp.composeapplicationstart.presentation.viewmodel.abs.ITrendingViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import net.bytebuddy.build.Plugin.Engine.Dispatcher

class MockTrendingViewModel: ITrendingViewModel {
    private val mockRepo = MockTrendingMovieRepo()
    override val trendingMovies: StateFlow<MovieUiState<List<TrendingMovieResponse?>>>
        get() = mockRepo.getTrendingMovie()
            .map { result ->
                when(result){
                    is Result.Success -> MovieUiState.UISuccess(result.data)
                    is Result.Error -> MovieUiState.UIError(result.error.asUiText())
                }
            }.stateIn(
                CoroutineScope(Dispatchers.IO),
                SharingStarted.WhileSubscribed(5),
                MovieUiState.UILoading
            )
}