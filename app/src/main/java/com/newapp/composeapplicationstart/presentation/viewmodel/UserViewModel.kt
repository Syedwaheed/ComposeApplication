package com.newapp.composeapplicationstart.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newapp.composeapplicationstart.data.utils.PasswordError
import com.newapp.composeapplicationstart.domain.repository.AuthRepository
import com.newapp.composeapplicationstart.data.utils.Result
import com.newapp.composeapplicationstart.domain.utils.UserDataValidator
import com.newapp.composeapplicationstart.presentation.utils.asUiText
import kotlinx.coroutines.launch

class UserViewModel(val userDataValidator: UserDataValidator,
    val authRepository: AuthRepository): ViewModel() {
    fun onRegisterClick(password:String){
        when(val result = userDataValidator.validatePassword(password)){
            is Result.Error ->{
                when(result.error){
                    PasswordError.LENGTH_SHORT -> TODO()
                    PasswordError.DIGIT_REQUIRED -> TODO()
                    PasswordError.NO_UPPERCASE -> TODO()
                }
            }
            is Result.Success ->{

            }
        }
        viewModelScope.launch {
            when(val result = authRepository.register(password)){
                is Result.Error -> {
                    val errorMessage = result.error.asUiText()
                }
                is Result.Success -> TODO()
            }
        }
    }
}