package com.example.wearos_ingestion.presentation.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wearos_ingestion.presentation.data.repository.HealthServicesRepository
import com.example.wearos_ingestion.presentation.data.repository.PassiveDataRepository

@Composable
fun NavigationController(
    healthServicesRepository: HealthServicesRepository,
    passiveDataRepository: PassiveDataRepository
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main_screen") {
        composable("main_screen") {
            // Your main screen content here
            SensorDataNavigationButton(navController = navController)
        }
        composable("sensor_data_screen") {
            // Screen displaying sensor data
            SensorDataScreen(healthServicesRepository, passiveDataRepository)
        }
    }
}