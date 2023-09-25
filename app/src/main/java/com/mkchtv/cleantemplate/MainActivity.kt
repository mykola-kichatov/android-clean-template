@file:OptIn(
    ExperimentalCoroutinesApi::class,
    ExperimentalAnimationApi::class,
    ExperimentalMaterial3Api::class
)

package com.mkchtv.cleantemplate

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import com.mkchtv.cleantemplate.nav.AppNavHost
import com.mkchtv.cleantemplate.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppContent()
        }
        getNotificationPermission(this)
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
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

fun getNotificationPermission(activity: Activity) {
    if (ActivityCompat.checkSelfPermission(
            activity,
            android.Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    )
        return
    try {
        if (Build.VERSION.SDK_INT > 32) {
            ActivityCompat.requestPermissions(
                activity, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                123
            )
        }
    } catch (e: Exception) {
    }
}