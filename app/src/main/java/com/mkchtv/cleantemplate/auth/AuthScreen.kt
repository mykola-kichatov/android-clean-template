package com.mkchtv.cleantemplate.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Button(onClick = {
            viewModel.onAuthClick()
        }) {
            Text(text = "Auth")
        }
    }
}
