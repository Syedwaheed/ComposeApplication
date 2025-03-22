package com.newapp.composeapplicationstart.presentation.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

sealed class UIText{
    data class DynamicText(val value: String): UIText()
    class StringResource(
        @StringRes val id: Int,
        val args: Array<Any> = arrayOf()
    ): UIText()

    @Composable
    fun asString():String{
        return when (this) {
            is DynamicText -> value
            is StringResource -> stringResource(id = id, *args)
//                LocalContext.current.getString(id,*args)
        }
    }

    fun asString(context: Context): String{
        return when(this){
            is DynamicText->value
            is StringResource -> context.getString(id, *args)
        }
    }
}