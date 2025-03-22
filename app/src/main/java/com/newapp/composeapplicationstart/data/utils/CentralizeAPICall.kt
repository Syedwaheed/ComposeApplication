package com.newapp.composeapplicationstart.data.utils

import com.google.gson.JsonParseException
import com.newapp.composeapplicationstart.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

fun <T,R> safeApiCall(
    apiCall: suspend () -> T?,
    mapper: (T) -> R
): Flow<Result<R, DataError.Network>> = flow {
    try {
        val data = apiCall()
        if(data != null){
            emit(Result.Success(mapper(data)))
        }else{
            emit(Result.Error(DataError.Network.SERIALIZATION))
        }
    } catch (e: HttpException) {
        emit(Result.Error(mapHttpExceptToNetWorkException(e)))
    } catch (e: IOException) {
        emit(Result.Error(DataError.Network.NO_INTERNET))
    }
    catch (e: Exception) {
        emit(Result.Error(DataError.Network.UNKNOWN_ERROR))
    }
}

fun <T> Flow<T>.mapToResult(): Flow<Result<T, DataError.Network>> = flow{
    try{
        collect{ data ->
            emit(Result.Success(data))
        }
    }catch (e:HttpException){
        emit(Result.Error(mapHttpExceptToNetWorkException(e)))
    }catch (e:IOException){
        emit(Result.Error(DataError.Network.NO_INTERNET))
    }catch (e:Exception){
        emit(Result.Error(DataError.Network.UNKNOWN_ERROR))
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