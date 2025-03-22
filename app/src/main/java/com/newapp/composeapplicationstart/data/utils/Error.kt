package com.newapp.composeapplicationstart.data.utils

sealed interface Error
enum class PasswordError: Error {
    LENGTH_SHORT,
    DIGIT_REQUIRED,
    NO_UPPERCASE
}