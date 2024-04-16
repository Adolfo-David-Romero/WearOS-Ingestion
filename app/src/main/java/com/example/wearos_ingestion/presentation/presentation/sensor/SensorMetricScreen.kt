package com.example.wearos_ingestion.presentation.presentation.sensor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
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
import com.example.wearos_ingestion.presentation.theme.IngestionAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import kotlin.math.roundToInt

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SensorMetricScreen(
    measureViewModel: MeasureDataViewModel,
    passiveViewModel: PassiveViewModel,
    elevationGainValue: Double,
    floorsValue: Double,
    caloriesValue: Double,
    dailyCaloriesValue: Double,
    dailyDistanceValue: Double,
    distanceValue: Double,
    dailyStepsValue: Double,
    stepsValue: Double,
    dailyFloorsValue: Double,
    enabled: Boolean,
    onButtonClick: () -> Unit,
    permissionState: PermissionState,
    navController: NavHostController
) {
    IngestionAppTheme {
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
                Text(text = "-----------------------------")
            }
            item {
                Text(text = "--Measure DataTypes--")
            }
            /*            item {
                            Text(text = "Elevation: $measureElevation")
                        }
                        item {
                            Text(text = "Pace: $measurePace")
                        }
                        item {
                            Text(text = "V_02 MAX: $measureV02Max")
                        }*/
            item {
                Text(text = "BPM: $measureHr")
            }
            item {
                Text(text = "Measure Capabilities: \n$measureSupportedDataTypes")
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
            /**Passive**/
            val supportedPassiveDataTypes by passiveViewModel.supportedDataTypesPassiveMonitoringNames

            item {
                Text(text = "-----------------------------")
            }
            item {
                Text(text = "--Passive DataTypes--")
            }
            item {
                Text(text = "Elevation Gain: ${(elevationGainValue)}")
            }
            item {
                Text(text = "Floors: ${(floorsValue)}")
            }
            item {
                Text(text = "Calories: ${"%.2f".format(caloriesValue)}")
            }
            item {
                Text(text = "Daily Calories: ${"%.2f".format(dailyCaloriesValue)}")
            }
            item {
                Text(text = "Daily Distance: ${"%.2f".format(dailyDistanceValue)}")
            }
            item {
                Text(text = "Distance: ${"%.2f".format(distanceValue)}")
            }
            item {
                Text(text = "Daily Steps: ${"%.2f".format(dailyStepsValue)}")
            }
            item {
                Text(text = "Steps: ${"%.2f".format(stepsValue)}")
            }
            item {
                Text(text = "Daily Floors: ${"%.2f".format(dailyFloorsValue)}")
            }
            item {
                Text(text = "-----------------------------")
            }
            item {
                Text(text = "Passive Capabilities: \n$supportedPassiveDataTypes")
            }
            item {
                //PassiveToggle(checked = enabled, onCheckedChange = onButtonClick, permissionState = )
            }

        }
    }


}
