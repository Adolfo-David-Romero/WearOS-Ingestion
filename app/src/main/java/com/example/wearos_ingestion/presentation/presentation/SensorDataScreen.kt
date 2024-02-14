package com.example.wearos_ingestion.presentation.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.MaterialTheme
import com.example.wearos_ingestion.R
import com.example.wearos_ingestion.presentation.data.repository.HealthServicesRepository
import com.example.wearos_ingestion.presentation.data.repository.PassiveDataRepository
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.delay
import kotlin.math.roundToInt


@Composable
fun SensorDataScreen(
    healthServicesRepository: HealthServicesRepository,
    passiveDataRepository: PassiveDataRepository
) {
    val viewModel: IngestionViewModel = viewModel(
        factory = IngestionViewModelFactory(
            healthServicesRepository = healthServicesRepository,
            passiveDataRepository = passiveDataRepository
        )
    )
    val hrValue by viewModel.hrValue.collectAsState()

    Column {
        val hrText = hrValue.roundToInt().toString()
        Text(hrText)

    }
}


