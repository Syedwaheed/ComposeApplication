package com.newapp.composeapplicationstart.data.repository

import com.newapp.composeapplicationstart.domain.model.response.UserData
import com.newapp.composeapplicationstart.domain.repository.AuthRepository
import com.newapp.composeapplicationstart.data.utils.DataError
import com.newapp.composeapplicationstart.data.utils.Result
import retrofit2.HttpException

class AuthRepositoryImpl: AuthRepository {
    override suspend fun register(password: String): Result<UserData, DataError.Network> {
        //API Call
        return try {
            val user = UserData("Waheed","123","abd")
            Result.Success(user)
        }catch (e:HttpException){
            when(e.code()){
                400 -> Result.Error(DataError.Network.BAD_REQUEST)
                401 -> Result.Error(DataError.Network.UN_AUTHORIZE)
                404 -> Result.Error(DataError.Network.NOT_FOUND)
                else -> Result.Error(DataError.Network.UNKNOWN_ERROR)
            }
        }
    }

}