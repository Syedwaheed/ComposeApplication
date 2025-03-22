package com.newapp.composeapplicationstart.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newapp.composeapplicationstart.data.utils.Result
import com.newapp.composeapplicationstart.domain.model.response.TrendingMovieResponse
import com.newapp.composeapplicationstart.domain.usecase.TrendingMovieUseCase
import com.newapp.composeapplicationstart.presentation.utils.EncryptedSharedPrefKeyValue
import com.newapp.composeapplicationstart.presentation.utils.MovieUiState
import com.newapp.composeapplicationstart.presentation.utils.asUiText
import com.newapp.composeapplicationstart.presentation.viewmodel.abs.ITrendingViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import okhttp3.internal.wait
import javax.inject.Inject
@HiltViewModel
class TrendingViewModel @Inject constructor(
    trendingMovieUseCase: TrendingMovieUseCase,
    prefKeyValue: EncryptedSharedPrefKeyValue
): ViewModel(), ITrendingViewModel {

    init {
        prefKeyValue.addAccessToken()
    }
    override val trendingMovies: StateFlow<MovieUiState<List<TrendingMovieResponse?>>> = trendingMovieUseCase()
        .flowOn(Dispatchers.IO)
        .map { result ->
            when(result){
                is Result.Success -> MovieUiState.UISuccess(result.data)
                is Result.Error -> MovieUiState.UIError(result.error.asUiText())
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5),
            MovieUiState.UILoading
        )
}