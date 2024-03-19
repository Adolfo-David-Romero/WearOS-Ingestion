/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.wearos_ingestion.presentation.app

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import com.example.wearos_ingestion.presentation.presentation.passive.PassiveDataApp
import com.example.wearos_ingestion.presentation.presentation.measure.MeasureDataApp


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val healthServicesRepository = (application as MainApplication).healthServicesRepository
        val passiveDataRepository = (application as MainApplication).passiveDataRepository

        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "passiveDataApp") {
                composable("passiveDataApp") {
                    PassiveDataApp(
                        healthServicesRepository = healthServicesRepository,
                        passiveDataRepository = passiveDataRepository,
                        navController = navController
                    )
                }
                composable("measureDataApp") {
                    //MeasuereDataScreen(healthServicesRepository, passiveDataRepository, navController)
                    MeasureDataApp(
                        healthServicesRepository = healthServicesRepository,
                        navController = navController)
                }
                composable("userActivityRecognitionScreen") {
                    //MeasuereDataScreen(healthServicesRepository, passiveDataRepository, navController)
                    MeasureDataApp(
                        healthServicesRepository = healthServicesRepository,
                        navController = navController)
                }
            }
        }
    }

}










