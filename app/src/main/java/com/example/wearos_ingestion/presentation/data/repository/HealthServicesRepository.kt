package com.example.wearos_ingestion.presentation.data.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.health.services.client.HealthServices
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.PassiveListenerConfig
import androidx.concurrent.futures.await
//import androidx.health.services.client.data.DataType.Companion.ABSOLUTE_ELEVATION
import com.example.wearos_ingestion.presentation.service.PassiveDataService



class HealthServicesRepository(context: Context) {
    private val healthServicesClient = HealthServices.getClient(context)
    private val passiveMonitoringClient = healthServicesClient.passiveMonitoringClient
    private val dataTypes = setOf(DataType.HEART_RATE_BPM)

    private val passiveListenerConfig = PassiveListenerConfig(
        dataTypes = dataTypes,
        shouldUserActivityInfoBeRequested = false,
        dailyGoals = setOf(),
        healthEventTypes = setOf()
    )

    suspend fun hasHeartRateCapability(): Boolean {
        val capabilities = passiveMonitoringClient.getCapabilitiesAsync().await()
        return DataType.HEART_RATE_BPM in capabilities.supportedDataTypesPassiveMonitoring
    }
    // Method to check if the device has elevation capability
    suspend fun hasElevationCapability(): Boolean {
        val capabilities = passiveMonitoringClient.getCapabilitiesAsync().await()
        return DataType.ABSOLUTE_ELEVATION in capabilities.supportedDataTypesPassiveMonitoring
    }



    suspend fun registerForHeartRateData() {
        Log.i(TAG, "Registering listener")
        passiveMonitoringClient.setPassiveListenerServiceAsync(
            PassiveDataService::class.java,
            passiveListenerConfig
        ).await()
    }
    // Method to register for elevation data
    suspend fun registerForElevationData() {
        if (hasElevationCapability()) {
            Log.i(TAG, "Registering for elevation data")
            passiveMonitoringClient.setPassiveListenerServiceAsync(
                PassiveDataService::class.java,
                passiveListenerConfig
            ).await()
        } else {
            Log.i(TAG, "Elevation data not supported on this device")
        }
    }

    suspend fun unregisterForHeartRateData() {
        Log.i(TAG, "Unregistering listeners")
        passiveMonitoringClient.clearPassiveListenerServiceAsync().await()
    }



}
