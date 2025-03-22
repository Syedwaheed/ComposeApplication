package com.newapp.composeapplicationstart.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newapp.composeapplicationstart.domain.model.response.MovieResponse
import com.newapp.composeapplicationstart.domain.usecase.GetPopularMoviesUseCase
import com.newapp.composeapplicationstart.data.utils.Result
import com.newapp.composeapplicationstart.presentation.utils.MovieUiState
import com.newapp.composeapplicationstart.presentation.utils.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    //    private val _movieUiState =
//        MutableStateFlow<MovieUiState<List<MovieResponse?>?>>(MovieUiState.UILoading)
//    val uiState = _movieUiState.asStateFlow()
//
//    init {
//        getMovies()
//    }
//
//    private fun getMovies() {
//        viewModelScope.launch {
//            getPopularMoviesUseCase()
//                .collect { result ->
//                    _movieUiState.value = when (result) {
//                        is Result.Success -> MovieUiState.UISuccess(data = result.data)
//                        is Result.Error -> MovieUiState.UIError(result.error.asUiText())
//                    }
//                }
//        }
//    }
    val uiState: StateFlow<MovieUiState<List<MovieResponse?>?>> =
        getPopularMoviesUseCase()
            .flowOn(Dispatchers.IO)
            .map { result ->
                when (result) {
                    is Result.Success -> MovieUiState.UISuccess(result.data)
                    is Result.Error -> MovieUiState.UIError(result.error.asUiText())
                }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5),
                MovieUiState.UILoading
            )

}