package com.newapp.composeapplicationstart.presentation.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun PlayScreen(name: String) {
    Box(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)){
        Text(text = name)
    }
}