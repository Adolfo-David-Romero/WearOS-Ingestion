package com.example.wearos_ingestion.presentation.app

import androidx.navigation.NavController

sealed class Screen(
    val route: String
) {
    object Ingestion : Screen("passive")
    object SensorData : Screen("sensor_data")
}
fun NavController.navigateToTopLevel(screen: Screen, route: String = screen.route) {
    navigate(route) {
        popUpTo(graph.id) {
            inclusive = true
        }
    }
}