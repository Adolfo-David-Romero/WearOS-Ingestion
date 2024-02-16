package com.example.wearos_ingestion.presentation.presentation.ingestion

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.TimeText
import com.example.wearos_ingestion.presentation.app.PERMISSION
import com.example.wearos_ingestion.presentation.data.repository.HealthServicesRepository
import com.example.wearos_ingestion.presentation.data.repository.PassiveDataRepository
import com.example.wearos_ingestion.presentation.theme.IngestionAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun IngestionApp(
    healthServicesRepository: HealthServicesRepository,
    passiveDataRepository: PassiveDataRepository,
    navController: NavHostController
) {

    IngestionAppTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            timeText = { TimeText() }
        ) {
            val viewModel: IngestionViewModel = viewModel(
                factory = IngestionViewModelFactory(
                    healthServicesRepository = healthServicesRepository,
                    passiveDataRepository = passiveDataRepository
                )
            )
            val hrValue by viewModel.hrValue.collectAsState()
            val hrEnabled by viewModel.hrEnabled.collectAsState()
            val uiState by viewModel.uiState

            if (uiState == UiState.Supported) {
                val permissionState = rememberPermissionState(
                    permission = PERMISSION,
                    onPermissionResult = { granted ->
                        if (granted) viewModel.toggleEnabled()
                    }
                )
                IngestionAppScreen(
                    hrValue = hrValue,
                    hrEnabled = hrEnabled,
                    onEnableClick = { viewModel.toggleEnabled() },
                    permissionState = permissionState,
                    navController = navController
                )
            } else if (uiState == UiState.NotSupported) {
                NotSupportedScreen()
            }
        }
    }
}