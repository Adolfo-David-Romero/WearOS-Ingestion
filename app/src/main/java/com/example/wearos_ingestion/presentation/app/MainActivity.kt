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
import com.example.wearos_ingestion.presentation.presentation.ingestion.IngestionApp
import com.example.wearos_ingestion.presentation.presentation.sensors.SensorDataScreen


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val healthServicesRepository = (application as MainApplication).healthServicesRepository
        val passiveDataRepository = (application as MainApplication).passiveDataRepository

        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "ingestionApp") {
                composable("ingestionApp") {
                    IngestionApp(
                        healthServicesRepository = healthServicesRepository,
                        passiveDataRepository = passiveDataRepository,
                        navController = navController
                    )
                }
                composable("sensorDataScreen") {
                    SensorDataScreen(healthServicesRepository, passiveDataRepository, navController)
                }
            }
        }
    }
}










