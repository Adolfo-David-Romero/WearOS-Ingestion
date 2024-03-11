package com.example.wearos_ingestion.presentation.presentation.measure

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.TimeText
import com.example.wearos_ingestion.presentation.app.PERMISSION
import com.example.wearos_ingestion.presentation.data.repository.HealthServicesRepository
import com.example.wearos_ingestion.presentation.theme.IngestionAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MeasureDataApp(
    healthServicesRepository: HealthServicesRepository,
    navController: NavHostController
) {
    IngestionAppTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            timeText = { TimeText() }
        ) {
            val viewModel: MeasureDataViewModel = viewModel(
                factory = MeasureDataViewModelFactory(
                    healthServicesRepository = healthServicesRepository
                )
            )
            val enabled by viewModel.enabled.collectAsState()
            val hr by viewModel.hr
            val availability by viewModel.availability
            val uiState by viewModel.uiState

            if (uiState == UiState.Supported) {
                val permissionState = rememberPermissionState(
                    permission = PERMISSION,
                    onPermissionResult = { granted ->
                        if (granted) viewModel.toggleEnabled()
                    }
                )
                MeasureDataScreen(
                    hr = hr,
                    availability = availability,
                    enabled = enabled,
                    onButtonClick = { viewModel.toggleEnabled() },
                    permissionState = permissionState,
                    navController = navController
                )
            } else if (uiState == UiState.NotSupported) {
                NotSupportedScreen()
            }
        }
    }
}