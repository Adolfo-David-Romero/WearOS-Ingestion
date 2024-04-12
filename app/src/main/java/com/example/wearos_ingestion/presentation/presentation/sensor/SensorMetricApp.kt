package com.example.wearos_ingestion.presentation.presentation.sensor

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.TimeText
import com.example.wearos_ingestion.presentation.app.LOCATIONPERMISSION
import com.example.wearos_ingestion.presentation.data.repository.HealthServicesRepository
import com.example.wearos_ingestion.presentation.data.repository.PassiveDataRepository
import com.example.wearos_ingestion.presentation.presentation.measure.MeasureDataViewModel
import com.example.wearos_ingestion.presentation.presentation.measure.MeasureDataViewModelFactory
import com.example.wearos_ingestion.presentation.presentation.measure.NotSupportedScreen
import com.example.wearos_ingestion.presentation.presentation.measure.UiState
import com.example.wearos_ingestion.presentation.presentation.passive.IngestionViewModelFactory
import com.example.wearos_ingestion.presentation.presentation.passive.PassiveViewModel
import com.example.wearos_ingestion.presentation.theme.IngestionAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SensorMetricApp(
    healthServicesRepository: HealthServicesRepository,
    passiveDataRepository: PassiveDataRepository,
    navController: NavHostController
) {
    IngestionAppTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            timeText = { TimeText() }
        ) {



            val measureViewModel: MeasureDataViewModel = viewModel(
                factory = MeasureDataViewModelFactory(
                    healthServicesRepository = healthServicesRepository
                )
            )
            val passiveViewModel: PassiveViewModel = viewModel(
                factory = IngestionViewModelFactory(
                    healthServicesRepository = healthServicesRepository,
                    passiveDataRepository = passiveDataRepository
                )
            )
            val enabled by measureViewModel.enabled.collectAsState()
            val uiState by measureViewModel.uiState

            if (uiState == UiState.Supported) {
                val permissionState = rememberPermissionState(
                    permission = LOCATIONPERMISSION ,
                    onPermissionResult = { granted ->
                        if (granted) measureViewModel.toggleEnabled()
                    }
                )
                SensorMetricScreen(
                    measureViewModel = measureViewModel,
                    passiveViewModel = passiveViewModel,
                    enabled = enabled,
                    onButtonClick = { measureViewModel.toggleEnabled() },
                    permissionState = permissionState,
                    navController = navController
                )

            } else if (uiState == UiState.NotSupported) {
                NotSupportedScreen()
            }
        }
    }
}