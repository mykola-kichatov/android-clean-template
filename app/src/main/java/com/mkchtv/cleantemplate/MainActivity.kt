@file:OptIn(
    ExperimentalCoroutinesApi::class,
    ExperimentalAnimationApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalSharedTransitionApi::class,
)

package com.mkchtv.cleantemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.mkchtv.cleantemplate.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppContent()
        }
    }
}

@Composable
private fun AppContent() {
    AppTheme {
        val navController = rememberNavController()
        Surface(tonalElevation = 5.dp) {
            Scaffold { innerPadding ->
                AppNavHost(
                    navController = navController,
                    modifier = Modifier
                        .padding(innerPadding)
                        .consumeWindowInsets(innerPadding),
                )
            }
        }
    }
}
