package com.newapp.composeapplicationstart.data.utils

import android.util.Log
import com.google.gson.JsonParseException
import com.newapp.composeapplicationstart.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import kotlin.coroutines.cancellation.CancellationException

fun <T, R> safeApiCall(
    apiCall: suspend () -> T?,
    mapper: (T) -> R
): Flow<Result<R, DataError.Network>> = flow<Result<R, DataError.Network>> {
    val data = apiCall()
    if (data != null) {
        emit(Result.Success(mapper(data)))
    } else {
        emit(Result.Error(DataError.Network.SERIALIZATION))
    }
}.catch { e ->
    when (e) {
        is HttpException -> emit(Result.Error(mapHttpExceptToNetWorkException(e)))
        is IOException -> emit(Result.Error(DataError.Network.NO_INTERNET))
        is CancellationException -> throw e // Respect coroutine cancellation
        else -> emit(Result.Error(DataError.Network.UNKNOWN_ERROR))
    }
}


private fun mapHttpExceptToNetWorkException(e: HttpException): DataError.Network {
    val error = when (e.code()) {
        400 -> DataError.Network.BAD_REQUEST
        401 -> DataError.Network.UN_AUTHORIZE
        404 -> DataError.Network.NOT_FOUND
        else -> DataError.Network.UNKNOWN_ERROR
    }
    return error
}

fun <T,R> List<T>?.mapIfNotNullList(mapper:(T) -> R):List<R>{
    return this?.map(mapper).orEmpty()
}
fun <T,R> T?.mapIfNotNull(mapper: (T) -> R): R?{
    return this?.let(mapper)
}