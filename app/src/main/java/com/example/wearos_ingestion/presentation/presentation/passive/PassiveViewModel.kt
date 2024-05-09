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
    //Data type value
    val hrValue = passiveDataRepository.latestHeartRate
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Double.NaN)
    val elevationGainValue = passiveDataRepository.latestElevationGain
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Double.NaN)
    val floorsValue = passiveDataRepository.latestFloors
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Double.NaN)
    val caloriesValue = passiveDataRepository.latestCalories
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Double.NaN)
    val dailyCaloriesValue = passiveDataRepository.latestDailyCalories
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Double.NaN)
    val dailyDistanceValue = passiveDataRepository.latestDailyDistance
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Double.NaN)
    val distanceValue = passiveDataRepository.latestDistance
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Double.NaN)
    val dailyStepsValue = passiveDataRepository.latestDailySteps
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Double.NaN)
    val stepsValue = passiveDataRepository.latestSteps
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Double.NaN)
    val dailyFloorsValue = passiveDataRepository.latestDailyFloors
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Double.NaN)


    val supportedDataTypesPassiveMonitoringNames: MutableState<Set<String>> =
        mutableStateOf(emptySet())

    //enabled data types
    val hrEnabled = passiveDataRepository.passiveDataEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val elevationGainEnabled = passiveDataRepository.passiveDataEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val floorsEnabled = passiveDataRepository.passiveDataEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)
    val caloriesEnabled = passiveDataRepository.passiveDataEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)
    val dailyCaloriesEnabled = passiveDataRepository.passiveDataEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)
    val dailyDistanceEnabled = passiveDataRepository.passiveDataEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)
    val distanceEnabled = passiveDataRepository.passiveDataEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)
    val dailyStepsEnabled = passiveDataRepository.passiveDataEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)
    val stepsEnabled = passiveDataRepository.passiveDataEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)
    val dailyFloorsEnabled = passiveDataRepository.passiveDataEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)
    val dailyElevationGainEnabled = passiveDataRepository.passiveDataEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val uiState: MutableState<UiState> = mutableStateOf(UiState.Startup)


    init {
        viewModelScope.launch {
            val supported = (
                    healthServicesRepository.hasPassiveHeartRateCapability()
                            && healthServicesRepository.hasPassiveElevationGainCapability()
                            && healthServicesRepository.hasPassiveFloorsCapability()
                            && healthServicesRepository.hasPassiveCaloriesCapability()
                            && healthServicesRepository.hasPassiveDailyCaloriesCapability()
                            && healthServicesRepository.hasPassiveDailyDistanceCapability()
                            && healthServicesRepository.hasPassiveDistanceCapability()
                            && healthServicesRepository.hasPassiveDailyStepsCapability()
                            && healthServicesRepository.hasPassiveStepsCapability()
                            && healthServicesRepository.hasPassiveDailyStepsCapability()
                    )
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
                    healthServicesRepository.registerForElevationGainData()
                    healthServicesRepository.registerForFloorsData()
                    healthServicesRepository.registerForCaloriesData()
                    healthServicesRepository.registerForDailyCaloriesData()
                    healthServicesRepository.registerForDailyDistanceData()
                    healthServicesRepository.registerForDistanceData()
                    healthServicesRepository.registerForDailyStepsData()
                    healthServicesRepository.registerForStepsData()
                    healthServicesRepository.registerForDailyFloorsData()

                } else {
                    healthServicesRepository.unregisterForHeartRateData()
                    healthServicesRepository.unregisterForElevationGainData()
                    healthServicesRepository.unregisterForFloorsData()
                    healthServicesRepository.unregisterForCaloriesData()
                    healthServicesRepository.unregisterForDailyCaloriesData()
                    healthServicesRepository.unregisterForDailyDistanceData()
                    healthServicesRepository.unregisterForDistanceData()
                    healthServicesRepository.unregisterForDailyStepsData()
                    healthServicesRepository.unregisterForStepsData()
                    healthServicesRepository.unregisterForDailyFloorsData()
                }
            }
        }

        /*        viewModelScope.launch {
                    passiveDataRepository.passiveDataEnabled.distinctUntilChanged().collect { enabled ->
                        if (enabled) {
                            healthServicesRepository.registerForElevationGainData()
                            healthServicesRepository.registerForElevationGainData()
                            healthServicesRepository.registerForFloorsData()
                        } else {
                            healthServicesRepository.unregisterForElevationGainData()
                            healthServicesRepository.unregisterForElevationGainData()
                            healthServicesRepository.unregisterForFloorsData()
                        }
                    }
                }*/
        //avail data types
        viewModelScope.launch {
            val supportedDataTypes = getSupportedDataTypesPassiveMonitoring()
            val dataTypeNames = supportedDataTypes.map { it.name }.toSet()
            supportedDataTypesPassiveMonitoringNames.value = dataTypeNames
            dataTypeNames.forEach { dataTypeName ->
                println("Supported data type for passive monitoring: \n$dataTypeName")
            }
        }


    }

    fun toggleEnabled() {
        viewModelScope.launch {
            val newEnabledStatus =
                !hrEnabled.value ||
                        !elevationGainEnabled.value ||
                        !floorsEnabled.value ||
                        !caloriesEnabled.value ||
                        !dailyCaloriesEnabled.value ||
                        !dailyDistanceEnabled.value ||
                        !distanceEnabled.value ||
                        !dailyStepsEnabled.value ||
                        !stepsEnabled.value ||
                        !dailyFloorsEnabled.value
            passiveDataRepository.setPassiveDataEnabled(newEnabledStatus)
            if (!newEnabledStatus) {
                // If HR is now disabled, wipe the last value.
                passiveDataRepository.storeLatestHeartRate(Double.NaN)
                passiveDataRepository.storeLatestElevationGain(Double.NaN)
                passiveDataRepository.storeLatestFloors(Double.NaN)
                passiveDataRepository.storeLatestCalories(Double.NaN)
                passiveDataRepository.storeLatestDailyCalories(Double.NaN)
                passiveDataRepository.storeLatestDailyDistance(Double.NaN)
                passiveDataRepository.storeLatestDistance(Double.NaN)
                passiveDataRepository.storeLatestDailySteps(Long.MIN_VALUE)
                passiveDataRepository.storeLatestSteps(Long.MIN_VALUE)
                passiveDataRepository.storeLatestDailyFloors(Double.NaN)
            }
        }
    }

    suspend fun getSupportedDataTypesPassiveMonitoring(): Set<DataType<*, *>> {
        val capabilities =
            healthServicesRepository.passiveMonitoringClient.getCapabilitiesAsync().await()
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
