package com.example.wearos_ingestion.presentation.presentation.measure

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.concurrent.futures.await
import androidx.health.services.client.data.DataTypeAvailability
import androidx.health.services.client.data.DeltaDataType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.wearos_ingestion.presentation.data.repository.HealthServicesRepository
import com.example.wearos_ingestion.presentation.data.repository.MeasureMessage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class MeasureDataViewModel(
    private val healthServicesRepository: HealthServicesRepository
) : ViewModel() {
    val enabled: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val hr: MutableState<Double> = mutableDoubleStateOf(0.0)
    val elevation: MutableState<Double> = mutableDoubleStateOf(0.0) // Elevation level
    val pace: MutableState<Double> = mutableDoubleStateOf(0.0) // Elevation level
    val v02Max: MutableState<Double> = mutableDoubleStateOf(0.0) // Elevation level
    val walkingTotal: MutableState<Long> = mutableLongStateOf(0L) // Elevation level
    val availability: MutableState<DataTypeAvailability> =
        mutableStateOf(DataTypeAvailability.UNKNOWN)
    val supportedDataTypes: MutableState<String> = mutableStateOf("") // List to store supported data type names


    val uiState: MutableState<UiState> = mutableStateOf(UiState.Startup)
/*    suspend fun getSupportedDataTypesMeasure(): Set<DeltaDataType<*, *>> {
        val capabilities = healthServicesRepository.measureClient.getCapabilitiesAsync().await()
        return capabilities.supportedDataTypesMeasure
    }*/

    init {
        viewModelScope.launch {

            uiState.value =
                if (healthServicesRepository.hasMeasuredHeartRateCapability()) {
                    UiState.Supported
                } else {
                    UiState.NotSupported
                }
        }
        viewModelScope.launch {
            enabled.collect {
                if (it) {
                    healthServicesRepository.heartRateMeasureFlow()
                        .takeWhile { enabled.value }
                        .collect { measureMessage ->
                            when (measureMessage) {
                                is MeasureMessage.MeasureData -> {
                                    hr.value = measureMessage.data.last().value
                                }

                                is MeasureMessage.MeasureAvailability -> {
                                    availability.value = measureMessage.availability
                                }


                            }
                        }
                }
            }
        }
        viewModelScope.launch {
            enabled.collect {
                if (it) {
                    healthServicesRepository.elevationMeasureFlow()
                        .takeWhile { enabled.value }
                        .collect { measureMessage ->
                            when (measureMessage) {
                                is MeasureMessage.MeasureData -> {
                                    elevation.value = measureMessage.data.last().value
                                }

                                is MeasureMessage.MeasureAvailability -> {
                                    availability.value = measureMessage.availability
                                }
                            }
                        }
                }

            }
        }
        viewModelScope.launch {
            enabled.collect {
                if (it) {
                    healthServicesRepository.paceMeasureFlow()
                        .takeWhile { enabled.value }
                        .collect { measureMessage ->
                            when (measureMessage) {
                                is MeasureMessage.MeasureData -> {
                                    pace.value = measureMessage.data.last().value
                                }

                                is MeasureMessage.MeasureAvailability -> {
                                    availability.value = measureMessage.availability
                                }
                            }
                        }
                }
            }
        }
        viewModelScope.launch {
            enabled.collect {
                if (it) {
                    healthServicesRepository.v02MaxMeasureFlow()
                        .takeWhile { enabled.value }
                        .collect { measureMessage ->
                            when (measureMessage) {
                                is MeasureMessage.MeasureData -> {
                                    v02Max.value = measureMessage.data.last().value
                                }

                                is MeasureMessage.MeasureAvailability -> {
                                    availability.value = measureMessage.availability
                                }
                            }
                        }
                }
            }
        }
        viewModelScope.launch {
            supportedDataTypes.value = (getSupportedDataTypesMeasure().map { it.name }.toString()) // Add names of supported data types to the list
        }



    }

    private suspend fun getSupportedDataTypesMeasure(): Set<DeltaDataType<*, *>> {
        val capabilities = healthServicesRepository.measureClient.getCapabilitiesAsync().await()
        return capabilities.supportedDataTypesMeasure
    }
    fun toggleEnabled() {
        enabled.value = !enabled.value
        if (!enabled.value) {
            availability.value = DataTypeAvailability.UNKNOWN
        }
    }
}

class MeasureDataViewModelFactory(
    private val healthServicesRepository: HealthServicesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MeasureDataViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MeasureDataViewModel(
                healthServicesRepository = healthServicesRepository
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
