package com.example.wearos_ingestion.presentation.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SensorDataNavigationButton(navController: NavController) {
    Button(
        onClick = { navController.navigate("sensor_data_screen") },
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(text = "View Sensor Data")
    }
}