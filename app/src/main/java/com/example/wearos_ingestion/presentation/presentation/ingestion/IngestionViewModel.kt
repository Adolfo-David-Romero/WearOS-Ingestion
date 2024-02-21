package com.example.wearos_ingestion.presentation.presentation.ingestion

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.wearos_ingestion.presentation.data.repository.HealthServicesRepository
import com.example.wearos_ingestion.presentation.data.repository.PassiveDataRepository
import com.example.wearos_ingestion.presentation.presentation.sensors.TAG
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class IngestionViewModel(
    private val healthServicesRepository: HealthServicesRepository,
    private val passiveDataRepository: PassiveDataRepository
) : ViewModel() {
    // Provides a hot flow of the latest HR value read from Data Store whilst there is an active
    // UI subscription. HR values are written to the Data Store in the [PassiveDataService] each
    // time an update is provided by Health Services.
    val hrValue = passiveDataRepository.latestHeartRate
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Double.NaN)

    val elevationValue = passiveDataRepository.latestElevation
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Double.NaN)


    val hrEnabled = passiveDataRepository.passiveDataEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)


    val uiState: MutableState<UiState> = mutableStateOf(UiState.Startup)

    init {
        viewModelScope.launch {
            val supported = healthServicesRepository.hasHeartRateCapability()
            uiState.value = if (supported) {
                UiState.Supported
            } else {
                UiState.NotSupported
            }
        }

        viewModelScope.launch {
            passiveDataRepository.passiveDataEnabled.distinctUntilChanged().collect { enabled ->
                if (enabled) {
                    healthServicesRepository.registerForHeartRateData()
                } else {
                    healthServicesRepository.unregisterForHeartRateData()
                }
            }
        }
        // Check for elevation capability and register for data
        viewModelScope.launch {
            if (healthServicesRepository.hasElevationCapability()) {
                healthServicesRepository.registerForElevationData()
            } else {
                Log.i(TAG, "Elevation data not supported on this device")
            }
        }
    }

    fun toggleEnabled() {
        viewModelScope.launch {
            val newEnabledStatus = !hrEnabled.value
            passiveDataRepository.setPassiveDataEnabled(newEnabledStatus)
            if (!newEnabledStatus) {
                // If HR is now disabled, wipe the last value.
                passiveDataRepository.storeLatestHeartRate(Double.NaN)
            }
        }
    }

}

class IngestionViewModelFactory(
    private val healthServicesRepository: HealthServicesRepository,
    private val passiveDataRepository: PassiveDataRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IngestionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return IngestionViewModel(
                healthServicesRepository = healthServicesRepository,
                passiveDataRepository = passiveDataRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

sealed class UiState {
    object Startup : UiState()
    object NotSupported : UiState()
    object Supported : UiState()
}
