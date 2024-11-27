package com.kappdev.txteditor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.ktx.isFlexibleUpdateAllowed
import com.kappdev.txteditor.editor_feature.data.Setting
import com.kappdev.txteditor.editor_feature.data.SettingsManager
import com.kappdev.txteditor.editor_feature.presentation.editor.components.EditorScreen
import com.kappdev.txteditor.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val updateSnackbarEvent = MutableSharedFlow<Unit>()
    private lateinit var appUpdateManager: AppUpdateManager

    @Inject
    lateinit var settingsManager: SettingsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appUpdateManager = AppUpdateManagerFactory.create(this)
        appUpdateManager.registerListener(installStateUpdateListener)
        checkForAppUpdates()

        setContent {
            val darkTheme by settingsManager.getValueAsState(Setting.Theme)
            AppTheme(darkTheme = darkTheme) {
                val snackbarHostState = remember { SnackbarHostState() }

                SetupSystemBarsColor()

                EditorScreen(settingsManager)

                LaunchedEffect(Unit) {
                    updateSnackbarEvent.collect {
                        val result = snackbarHostState.showSnackbar(
                            message = getString(R.string.the_new_app_version_is_ready),
                            actionLabel = getString(R.string.install),
                            duration = SnackbarDuration.Indefinite
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            appUpdateManager.completeUpdate()
                        }
                    }
                }

                SnackbarHost(snackbarHostState)
            }
        }
    }

    private fun checkForAppUpdates() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
            val isUpdateAvailable = info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
            val isUpdateAllowed = info.isFlexibleUpdateAllowed
            if (isUpdateAvailable && isUpdateAllowed) {
                val updateOptions = AppUpdateOptions.defaultOptions(AppUpdateType.FLEXIBLE)
                appUpdateManager.startUpdateFlow(info, this, updateOptions)
            } else if (info.installStatus() == InstallStatus.DOWNLOADED) {
                appUpdateManager.completeUpdate()
            }
        }
    }

    private val installStateUpdateListener = InstallStateUpdatedListener { state ->
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            lifecycleScope.launch {
                updateSnackbarEvent.emit(Unit)
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

    override fun onDestroy() {
        super.onDestroy()
        appUpdateManager.unregisterListener(installStateUpdateListener)
    }
}