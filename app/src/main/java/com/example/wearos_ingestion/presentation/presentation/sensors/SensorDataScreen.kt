package com.example.wearos_ingestion.presentation.presentation.sensors

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.health.services.client.HealthServices
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.MaterialTheme
import com.example.wearos_ingestion.R
import com.example.wearos_ingestion.presentation.data.repository.HealthServicesRepository
import com.example.wearos_ingestion.presentation.data.repository.PassiveDataRepository
import com.example.wearos_ingestion.presentation.presentation.ingestion.IngestionViewModel
import com.example.wearos_ingestion.presentation.presentation.ingestion.IngestionViewModelFactory
import com.example.wearos_ingestion.presentation.data.model.SensorDataModel
import com.example.wearos_ingestion.presentation.theme.IngestionAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.fitness.data.HealthDataTypes
import com.google.android.gms.fitness.data.HealthFields
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlin.math.roundToInt

const val TAG = "Sensor Metrics"
@Composable
fun SensorDataScreen(
    healthServicesRepository: HealthServicesRepository,
    passiveDataRepository: PassiveDataRepository,
    navController: NavHostController
) {
    //val sensorDataModel = remember { SensorDataModel() } // Remember the SensorDataModel instance
    IngestionAppTheme {
        Column {

            val viewModel: IngestionViewModel = viewModel(
                factory = IngestionViewModelFactory(
                    healthServicesRepository = healthServicesRepository,
                    passiveDataRepository = passiveDataRepository
                )
            )
            val hrValue by viewModel.hrValue.collectAsState()


            BackNavigationButton(navController = navController)
            Log.d(TAG, "current hr: $hrValue")
            Text("$hrValue", color = androidx.compose.ui.graphics.Color.Red)

            // In SensorDataScreen

            val elevationValue by viewModel.elevationValue.collectAsState()

            Text(
                text = "Elevation: $elevationValue meters",
                color = androidx.compose.ui.graphics.Color.Blue,
                modifier = Modifier.padding(8.dp)
            )






        }
    }
}


