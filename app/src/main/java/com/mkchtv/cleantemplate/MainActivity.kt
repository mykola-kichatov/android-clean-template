@file:OptIn(
    ExperimentalCoroutinesApi::class,
    ExperimentalAnimationApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalSharedTransitionApi::class,
)

package com.mkchtv.cleantemplate

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.mkchtv.cleantemplate.common.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        // Hold the splash while the stored theme is still unknown, so the app is drawn once in
        // the right colors instead of appearing in the phone's theme and flipping.
        splashScreen.setKeepOnScreenCondition { viewModel.appearance.value == null }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appearance by viewModel.appearance.collectAsStateWithLifecycle()
            appearance?.let { stored ->
                val darkTheme = stored.themeMode.useDarkTheme(isSystemInDarkTheme())
                // The bars keep the styles of the plain call above, but take dark mode from the
                // app theme. A setting can make it differ from the phone's, and then a dark app
                // on a light phone would get dark icons on its dark status bar.
                DisposableEffect(darkTheme) {
                    enableEdgeToEdge(
                        statusBarStyle = SystemBarStyle.auto(
                            lightScrim = Color.TRANSPARENT,
                            darkScrim = Color.TRANSPARENT,
                        ) { darkTheme },
                        navigationBarStyle = SystemBarStyle.auto(
                            lightScrim = defaultLightScrim,
                            darkScrim = defaultDarkScrim,
                        ) { darkTheme },
                    )
                    onDispose {}
                }
                AppContent(darkTheme = darkTheme, dynamicColor = stored.dynamicColor)
            }
        }
    }
}

@Composable
private fun AppContent(darkTheme: Boolean, dynamicColor: Boolean) {
    AppTheme(darkTheme = darkTheme, dynamicColor = dynamicColor) {
        val navController = rememberNavController()
        Surface {
            AppNavHost(
                navController = navController,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

// The navigation bar scrims of the plain enableEdgeToEdge(), which androidx keeps private. They
// show only before Android 10, which leaves the navigation bar contrast to the app.
private val defaultLightScrim = Color.argb(0xe6, 0xFF, 0xFF, 0xFF)
private val defaultDarkScrim = Color.argb(0x80, 0x1b, 0x1b, 0x1b)
