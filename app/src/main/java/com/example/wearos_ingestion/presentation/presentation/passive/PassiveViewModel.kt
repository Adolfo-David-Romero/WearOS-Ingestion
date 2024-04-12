package com.example.wearos_ingestion.presentation.presentation.passive

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.concurrent.futures.await
import androidx.health.services.client.data.DataType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.wearos_ingestion.presentation.data.repository.HealthServicesRepository
import com.example.wearos_ingestion.presentation.data.repository.PassiveDataRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PassiveViewModel(
    private val healthServicesRepository: HealthServicesRepository,
    private val passiveDataRepository: PassiveDataRepository
) : ViewModel() {
    // Provides a hot flow of the latest HR value read from Data Store whilst there is an active
    // UI subscription. HR values are written to the Data Store in the [PassiveDataService] each
    // time an update is provided by Health Services.
    val hrValue = passiveDataRepository.latestHeartRate
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Double.NaN)
    val elevationGainValue = passiveDataRepository.latestElevationGain
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Double.NaN).value


    //val supportedDataTypesPassiveMonitoring: MutableState<Set<DataType<*, *>>> = mutableStateOf(emptySet())
    val supportedDataTypesPassiveMonitoringNames: MutableState<Set<String>> = mutableStateOf(emptySet())

    val hrEnabled = passiveDataRepository.passiveDataEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)


    val uiState: MutableState<UiState> = mutableStateOf(UiState.Startup)



    init {
        viewModelScope.launch {
            val supported = healthServicesRepository.hasPassiveHeartRateCapability()
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

        viewModelScope.launch {
            passiveDataRepository.passiveDataEnabled.distinctUntilChanged().collect { enabled ->
                if (enabled) {
                    healthServicesRepository.registerForElevationGainData()
                } else {
                    healthServicesRepository.unregisterForElevationGainData()
                }
            }
        }
        //avail data types
        viewModelScope.launch {
            val supportedDataTypes = getSupportedDataTypesPassiveMonitoring()
            val dataTypeNames = supportedDataTypes.map { it.name }.toSet()
            supportedDataTypesPassiveMonitoringNames.value = dataTypeNames
            dataTypeNames.forEach { dataTypeName ->
                println("Supported data type for passive monitoring: $dataTypeName")
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
    suspend fun getSupportedDataTypesPassiveMonitoring(): Set<DataType<*, *>> {
        val capabilities = healthServicesRepository.passiveMonitoringClient.getCapabilitiesAsync().await()
        return capabilities.supportedDataTypesPassiveMonitoring
    }

}

class IngestionViewModelFactory(
    private val healthServicesRepository: HealthServicesRepository,
    private val passiveDataRepository: PassiveDataRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PassiveViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PassiveViewModel(
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
