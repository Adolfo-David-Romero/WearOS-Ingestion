package com.example.wearos_ingestion.presentation.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import com.example.wearos_ingestion.presentation.data.repository.HealthServicesRepository
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    healthServicesRepository: HealthServicesRepository
) {
    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        /**
        Navigation Button routing
         */
        item{
            ActivityRecognitionNavigationButton(navController = navController)
        }
        item{
            PassiveDataNavigationButton(navController = navController)
        }
        item{
            MeasureDataNavigationButton(navController = navController)
        }
        item{
            //ADL
            ADLNavigationButton(navController = navController)
        }
        item{
            //Sensor
            SensorMetricNavigationButton(navController = navController)
        }
        item{
            //Sensor
            CurrentLocationNavigationButton(navController = navController)
        }
        item{
            //Sensor
            GeoBubbleMapNavigationButton(navController = navController)
        }
        item{
            //Sensor
            UserFeedbackNavigationButton(navController = navController)
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
fun HomeScreenPreview() {
/*    val permissionState = object : PermissionState {
        override val permission = PERMISSION
        override val status: PermissionStatus = PermissionStatus.Granted
        override fun launchPermissionRequest() {}
    }*/

   /* val navController = rememberNavController() // Create a NavController instance
    IngestionAppTheme {
        HomeScreen(
            navController = navController
        )
    }*/
}