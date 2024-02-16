package com.example.wearos_ingestion.presentation.presentation.ingestion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import com.example.wearos_ingestion.presentation.app.PERMISSION
import com.example.wearos_ingestion.presentation.theme.IngestionAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun IngestionAppScreen(
    hrValue: Double,
    hrEnabled: Boolean,
    onEnableClick: (Boolean) -> Unit,
    permissionState: PermissionState,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HeartRateToggle(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            checked = hrEnabled,
            onCheckedChange = onEnableClick,
            permissionState = permissionState
        )
        HeartRateCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            heartRate = hrValue
        )
        // Spacer to push the button to the bottom
        Spacer(modifier = Modifier.weight(1f))

        /*val navController = rememberNavController() // Create a NavController instance
        SensorDataNavigationButton(navController = navController)*/
        Button(
            onClick = {
                navController.navigate("sensorDataScreen")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text("Go to Sensor Data Screen")
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
fun PassiveDataScreenPreview() {
    val permissionState = object : PermissionState {
        override val permission = PERMISSION
        override val status: PermissionStatus = PermissionStatus.Granted
        override fun launchPermissionRequest() {}
    }
    val navController = rememberNavController() // Create a NavController instance
    IngestionAppTheme {
        IngestionAppScreen(
            hrValue = 65.6,
            hrEnabled = true,
            onEnableClick = {},
            permissionState = permissionState,
            navController =  navController
        )
    }
}

