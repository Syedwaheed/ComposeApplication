package com.newapp.composeapplicationstart.presentation.utils


sealed class MovieUiState<out T> {
    data object UILoading : MovieUiState<Nothing>()
    data class UISuccess<out T>(val data: T) : MovieUiState<T>()
    data class UIError(val message: UIText) : MovieUiState<Nothing>()
}