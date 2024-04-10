package com.example.wearos_ingestion.presentation.presentation.sensor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.health.services.client.data.DataTypeAvailability
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import com.example.wearos_ingestion.R
import com.example.wearos_ingestion.presentation.app.PERMISSION
import com.example.wearos_ingestion.presentation.data.repository.HealthServicesRepository
import com.example.wearos_ingestion.presentation.presentation.measure.BackNavigationButton
import com.example.wearos_ingestion.presentation.presentation.measure.HrLabel
import com.example.wearos_ingestion.presentation.presentation.measure.MeasureDataScreen
import com.example.wearos_ingestion.presentation.presentation.measure.MeasureDataViewModel
import com.example.wearos_ingestion.presentation.presentation.measure.MeasureDataViewModelFactory
import com.example.wearos_ingestion.presentation.presentation.measure.NotSupportedScreen
import com.example.wearos_ingestion.presentation.presentation.measure.UiState
import com.example.wearos_ingestion.presentation.theme.IngestionAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SensorMetricScreen(
    viewModel: MeasureDataViewModel,
    enabled: Boolean,
    onButtonClick: () -> Unit,
    permissionState: PermissionState,
    navController: NavHostController
) {
    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val hr by viewModel.hr
        val elevation by viewModel.elevation
        val pace by viewModel.pace
        item {
            BackNavigationButton(navController = navController)
        }
        item {
            Text(text = "Elevation: $elevation")
        }
        item {
            Text(text = "Pace: $pace")
        }
        item {
            Text(text = "BPM: $hr")
        }
        item {
            Button(
                modifier = Modifier.fillMaxWidth(0.5f),
                onClick = {
                    if (permissionState.status.isGranted) {
                        onButtonClick()
                    } else {
                        permissionState.launchPermissionRequest()
                    }
                }
            ) {
                val buttonTextId = if (enabled) {
                    R.string.stop
                } else {
                    R.string.start
                }
                Text(stringResource(buttonTextId))
            }
        }
    }
}

@ExperimentalPermissionsApi
@Preview(
    device = Devices.WEAR_OS_SMALL_ROUND,
    showBackground = false,
    showSystemUi = true
)
@Composable
fun MeasureDataScreenPreview() {
    val permissionState = object : PermissionState {
        override val permission = "android.permission.ACTIVITY_RECOGNITION"
        override val status: PermissionStatus = PermissionStatus.Granted
        override fun launchPermissionRequest() {}
    }
    val navController = rememberNavController() // Create a NavController instance
    IngestionAppTheme {
        MeasureDataScreen(
            hr = 65.0,
            availability = DataTypeAvailability.AVAILABLE,
            enabled = false,
            onButtonClick = {},
            permissionState = permissionState,
            navController = navController
        )
    }
}