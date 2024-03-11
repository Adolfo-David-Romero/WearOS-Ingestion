package com.example.wearos_ingestion.presentation.presentation.measure

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.services.client.data.DataTypeAvailability
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.Button
import com.example.wearos_ingestion.R
import com.example.wearos_ingestion.presentation.theme.IngestionAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted

const val TAG = "Sensor Metrics"
/*@Composable
fun SensorDataScreen(
    healthServicesRepository: HealthServicesRepository,
    passiveDataRepository: PassiveDataRepository,
    navController: NavHostController
) {
    //val sensorDataModel = remember { SensorDataModel() } // Remember the SensorDataModel instance
    IngestionAppTheme {
        Column {

            val viewModel: PassiveViewModel = viewModel(
                factory = IngestionViewModelFactory(
                    healthServicesRepository = healthServicesRepository,
                    passiveDataRepository = passiveDataRepository
                )
            )
            val hrValue by viewModel.hrValue.collectAsState()
            val heartRateFlow = remember { healthServicesRepository.heartRateMeasureFlow() }
            //val heartRateData = collectAsState(initial = null, heartRateFlow)

            *//* Sensor Data screen *//*
            BackNavigationButton(navController = navController)
            Log.d(TAG, "current hr: $hrValue")
            Text("$hrValue", color = androidx.compose.ui.graphics.Color.Red)



            val elevationValue by viewModel.elevationValue.collectAsState()
            Log.i(TAG, "current ELEVATION: $elevationValue")
            Text(
                text = "Elevation: $elevationValue meters",
                color = androidx.compose.ui.graphics.Color.Blue,
                modifier = Modifier.padding(8.dp)
            )

        }
    }
}*/
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MeasureDataScreen(
    hr: Double,
    availability: DataTypeAvailability,
    enabled: Boolean,
    onButtonClick: () -> Unit,
    permissionState: PermissionState,
    navController: NavHostController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HrLabel(
            hr = hr,
            availability = availability
        )
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
            androidx.wear.compose.material.Text(stringResource(buttonTextId))
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



