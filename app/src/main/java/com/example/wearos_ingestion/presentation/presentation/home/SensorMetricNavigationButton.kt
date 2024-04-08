package com.example.wearos_ingestion.presentation.presentation.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SensorMetricNavigationButton(navController: NavController) {
    Button(
        onClick = {
            navController.navigate("sensorMetricApp")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text("Go to Sensor Metric/Diagnostic Screen")
    }

}