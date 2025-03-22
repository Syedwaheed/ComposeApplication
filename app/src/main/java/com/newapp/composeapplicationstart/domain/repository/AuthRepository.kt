package com.newapp.composeapplicationstart.domain.repository

import com.newapp.composeapplicationstart.domain.model.response.UserData
import com.newapp.composeapplicationstart.data.utils.DataError
import com.newapp.composeapplicationstart.data.utils.Result

interface AuthRepository{
    suspend fun register(password: String) : Result<UserData, DataError.Network>
}
