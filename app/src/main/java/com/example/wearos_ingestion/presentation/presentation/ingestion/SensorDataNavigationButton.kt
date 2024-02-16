package com.example.wearos_ingestion.presentation.presentation.ingestion

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
        onClick = {
            navController.navigate("sensorDataScreen")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text("Go to Sensor Data Screen")
    }

}