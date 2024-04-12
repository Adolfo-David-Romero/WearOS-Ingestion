package com.example.wearos_ingestion.presentation.presentation.sensor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import com.example.wearos_ingestion.R
import com.example.wearos_ingestion.presentation.presentation.measure.BackNavigationButton
import com.example.wearos_ingestion.presentation.presentation.measure.MeasureDataViewModel
import com.example.wearos_ingestion.presentation.presentation.passive.PassiveViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SensorMetricScreen(
    measureViewModel: MeasureDataViewModel,
    passiveViewModel: PassiveViewModel,
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

        /**Measure**/
        val measureHr by measureViewModel.hr
        val measureElevation by measureViewModel.elevation
        val measurePace by measureViewModel.pace
        val measureV02Max by measureViewModel.v02Max
        val measureSupportedDataTypes by measureViewModel.supportedDataTypes
        item {
            BackNavigationButton(navController = navController)
        }
        item {
            Text(text = "---------Measure DataTypes---------")
        }
        item {
            Text(text = "Elevation: $measureElevation")
        }
        item {
            Text(text = "Pace: $measurePace")
        }
        item {
            Text(text = "V_02 MAX: $measureV02Max")
        }
        item {
            Text(text = "BPM: $measureHr")
        }
        item {
            Text(text = "Measure Device Capabilities:\n $measureSupportedDataTypes")

        }
        /**Passive**/
        val supportedPassiveDataTypes by passiveViewModel.supportedDataTypesPassiveMonitoringNames
        //val passiveElevationGain by passiveViewModel.elevationGainValue


        item {
            Text(text = "---------Passive DataTypes---------")
        }
        item {
            //Text(text = "Elevation Gain: $passiveElevationGain")
        }

        item {
            Text(text = "Passive Device Capabilities:\n $supportedPassiveDataTypes")
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

/*
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
    val viewModel: MeasureDataViewModel = viewModel(
        factory = MeasureDataViewModelFactory(
            healthServicesRepository = healthServicesRepository
        )
    )
    val navController = rememberNavController() // Create a NavController instance
    IngestionAppTheme {
        SensorMetricScreen(
            viewModel = viewModel,
            enabled = false,
            onButtonClick = {},
            permissionState = permissionState,
            navController = navController
        )
    }
}*/
