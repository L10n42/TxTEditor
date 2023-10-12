package com.kappdev.txteditor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kappdev.txteditor.data.Setting
import com.kappdev.txteditor.data.SettingsManager
import com.kappdev.txteditor.presentation.editor.components.EditorScreen
import com.kappdev.txteditor.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var settingsManager: SettingsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val darkTheme by settingsManager.getValueAsState(Setting.Theme)
            AppTheme(darkTheme = darkTheme) {
                SetupSystemBarsColor()

                EditorScreen(settingsManager)
            }
        }
    }

    @Composable
    private fun SetupSystemBarsColor(
        statusBarColor: Color = MaterialTheme.colorScheme.background,
        navigationBarColor: Color = MaterialTheme.colorScheme.surface
    ) {
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setNavigationBarColor(navigationBarColor)
            systemUiController.setStatusBarColor(statusBarColor)
        }
    }
}