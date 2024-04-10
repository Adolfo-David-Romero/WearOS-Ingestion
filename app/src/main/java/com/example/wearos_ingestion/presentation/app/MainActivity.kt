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
import com.example.wearos_ingestion.presentation.presentation.activityrecognition.UserActivityRecognitionScreen
import com.example.wearos_ingestion.presentation.presentation.adl.ADLScreen
import com.example.wearos_ingestion.presentation.presentation.home.HomeScreen
import com.example.wearos_ingestion.presentation.presentation.passive.PassiveDataApp
import com.example.wearos_ingestion.presentation.presentation.measure.MeasureDataApp
import com.example.wearos_ingestion.presentation.presentation.sensor.SensorMetricApp
import com.example.wearos_ingestion.presentation.presentation.sensor.SensorMetricScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val healthServicesRepository = (application as MainApplication).healthServicesRepository
        val passiveDataRepository = (application as MainApplication).passiveDataRepository

        setContent {
            val navController = rememberNavController()
            //NavHost(navController = navController, startDestination = "passiveDataApp") {
            NavHost(navController = navController, startDestination = "homeScreen") {
                composable("homeScreen"){
                    HomeScreen(navController = navController, healthServicesRepository)
                }
                composable("passiveDataApp") {
                    PassiveDataApp(
                        healthServicesRepository = healthServicesRepository,
                        passiveDataRepository = passiveDataRepository,
                        navController = navController
                    )
                }
                composable("measureDataApp") {
                    MeasureDataApp(
                        healthServicesRepository = healthServicesRepository,
                        navController = navController
                    )
                }
                composable("userActivityRecognitionScreen") {
                    //UserActivityRecognitionContent(navController = navController)
                    UserActivityRecognitionScreen(navController)
                }
                composable("aDLScreen") {
                    //UserActivityRecognitionContent(navController = navController)
                    ADLScreen(navController)
                }
                composable("sensorMetricApp") {
                    //UserActivityRecognitionContent(navController = navController)
                    SensorMetricApp(healthServicesRepository, navController)
                }
            }
        }
    }

}










