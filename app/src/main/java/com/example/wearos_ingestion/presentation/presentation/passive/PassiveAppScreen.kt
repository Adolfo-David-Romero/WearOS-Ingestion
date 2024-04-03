package com.example.wearos_ingestion.presentation.presentation.passive

import androidx.compose.foundation.layout.Arrangement
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
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import com.example.wearos_ingestion.presentation.app.PERMISSION
import com.example.wearos_ingestion.presentation.presentation.home.ADLNavigationButton
import com.example.wearos_ingestion.presentation.presentation.home.ActivityRecognitionNavigationButton
import com.example.wearos_ingestion.presentation.presentation.home.MeasureDataNavigationButton
import com.example.wearos_ingestion.presentation.presentation.measure.BackNavigationButton
import com.example.wearos_ingestion.presentation.theme.IngestionAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PassiveAppScreen(
    hrValue: Double,
    hrEnabled: Boolean,
    onEnableClick: (Boolean) -> Unit,
    permissionState: PermissionState,
    navController: NavHostController
) {
    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        /*item{
            ActivityRecognitionNavigationButton(navController = navController)
        }
        item{
            //ADL
            ADLNavigationButton(navController = navController)
        }*/
        item {
            BackNavigationButton(navController = navController)
        }
        item {
            HeartRateToggle(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                checked = hrEnabled,
                onCheckedChange = onEnableClick,
                permissionState = permissionState
            )
        }
        item {
            HeartRateCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                heartRate = hrValue
            )
        }
        /*item {
            MeasureDataNavigationButton(navController = navController)
        }*/

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
        PassiveAppScreen(
            hrValue = 65.6,
            hrEnabled = true,
            onEnableClick = {},
            permissionState = permissionState,
            navController = navController
        )
    }
}

