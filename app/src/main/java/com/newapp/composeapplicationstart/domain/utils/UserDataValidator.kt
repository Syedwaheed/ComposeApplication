package com.newapp.composeapplicationstart.domain.utils

import com.newapp.composeapplicationstart.data.utils.Error
import com.newapp.composeapplicationstart.data.utils.PasswordError
import com.newapp.composeapplicationstart.data.utils.Result

class UserDataValidator {
    fun validatePassword(password:String) : Result<Unit, PasswordError> {
        if(password.length<9){
            return Result.Error(PasswordError.LENGTH_SHORT)
        }
        val isDigit = password.any { it.isDigit() }
        if(!isDigit){
            return Result.Error(PasswordError.DIGIT_REQUIRED)
        }
        val hasUpperCase = password.any{it.isUpperCase()}
        if(!hasUpperCase){
            return Result.Error(PasswordError.NO_UPPERCASE)
        }
        return Result.Success(Unit)
    }

}