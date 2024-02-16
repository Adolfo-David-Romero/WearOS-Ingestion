package com.example.wearos_ingestion.presentation.presentation.sensors

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.MaterialTheme
import com.example.wearos_ingestion.R
import com.example.wearos_ingestion.presentation.data.repository.HealthServicesRepository
import com.example.wearos_ingestion.presentation.data.repository.PassiveDataRepository
import com.example.wearos_ingestion.presentation.theme.IngestionAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@Composable
fun SensorDataScreen(
    navController: NavHostController
) {
    IngestionAppTheme {
        
        Column {
            Button(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text("Go back to IngestionAppScreen")
            }
            Text(text = "ssssss")
        }
    }
}


