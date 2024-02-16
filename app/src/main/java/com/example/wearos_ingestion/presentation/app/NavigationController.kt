package com.example.wearos_ingestion.presentation.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wearos_ingestion.presentation.data.repository.HealthServicesRepository
import com.example.wearos_ingestion.presentation.data.repository.PassiveDataRepository
import com.example.wearos_ingestion.presentation.presentation.ingestion.IngestionApp
import com.example.wearos_ingestion.presentation.presentation.ingestion.SensorDataNavigationButton
import com.example.wearos_ingestion.presentation.presentation.sensors.SensorDataScreen

/*
@Composable
fun NavigationController(
    healthServicesRepository: HealthServicesRepository,
    passiveDataRepository: PassiveDataRepository
) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "ingestionApp") {
        composable("ingestionApp") {
            IngestionApp(healthServicesRepository,passiveDataRepository, navController)
        }
        composable("sensorDataScreen") {
            SensorDataScreen()
        }
    }

}*/
