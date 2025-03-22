package com.newapp.composeapplicationstart.data.utils

sealed interface DataError : Error {
    enum class Network: DataError {
        REQUEST_TIMEOUT,
        SERVER_ERROR,
        NO_INTERNET,
        TOO_MANYREQUEST,
        SERIALIZATION,
        PAYLOAD_TOO_LARGE,
        UNKNOWN_ERROR,
        BAD_REQUEST,
        UN_AUTHORIZE,
        NOT_FOUND
    }
    enum class Local: DataError {
        DISK_FULL
    }
}