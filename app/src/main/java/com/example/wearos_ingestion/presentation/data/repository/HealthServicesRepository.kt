package com.example.wearos_ingestion.presentation.data.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.health.services.client.HealthServices
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.PassiveListenerConfig
import androidx.health.services.client.MeasureCallback
import androidx.health.services.client.data.Availability
import androidx.health.services.client.data.DataPointContainer
import androidx.health.services.client.data.DataTypeAvailability
import androidx.health.services.client.data.DeltaDataType
import androidx.health.services.client.data.SampleDataPoint
import androidx.concurrent.futures.await
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.example.wearos_ingestion.presentation.service.PassiveDataService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.runBlocking

/**
 * Hybrid Health Service Repository which works with both PassiveClient and MeasuredClient
 * ---
 * Entry point for [HealthServicesClient] APIs. This also provides suspend functions around
 * those APIs to enable use in coroutines.
 * */
class HealthServicesRepository(context: Context) {
    private val healthServicesClient = HealthServices.getClient(context)
    private val dataTypes = setOf(DataType.HEART_RATE_BPM)
    /** Passive Client **/
    private val passiveMonitoringClient = healthServicesClient.passiveMonitoringClient

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
    /*suspend fun hasElevationCapability(): Boolean {
        val capabilities = passiveMonitoringClient.getCapabilitiesAsync().await()
        return DataType.ABSOLUTE_ELEVATION in capabilities.supportedDataTypesPassiveMonitoring
    }*/



    suspend fun registerForHeartRateData() {
        Log.i(TAG, "Registering listener")
        passiveMonitoringClient.setPassiveListenerServiceAsync(
            PassiveDataService::class.java,
            passiveListenerConfig
        ).await()
    }
    // Method to register for elevation data
/*    suspend fun registerForElevationData() {
        if (hasElevationCapability()) {
            Log.i(TAG, "Registering for elevation data")
            passiveMonitoringClient.setPassiveListenerServiceAsync(
                PassiveDataService::class.java,
                passiveListenerConfig
            ).await()
        } else {
            Log.i(TAG, "Elevation data not supported on this device")
        }
    }*/

    suspend fun unregisterForHeartRateData() {
        Log.i(TAG, "Unregistering listeners")
        passiveMonitoringClient.clearPassiveListenerServiceAsync().await()
    }

    /** Measure Client **/
    private val measureClient = healthServicesClient.measureClient
    suspend fun hasMeasuredHeartRateCapability(): Boolean {
        val capabilities = measureClient.getCapabilitiesAsync().await()
        return (DataType.HEART_RATE_BPM in capabilities.supportedDataTypesMeasure)
    }
    suspend fun hasMeasuredElevationCapability(): Boolean {
        val capabilities = measureClient.getCapabilitiesAsync().await()
        return (DataType.ABSOLUTE_ELEVATION in capabilities.supportedDataTypesMeasure)
    }
/*    suspend fun hasMeasuredElevationStatsCapability(): Boolean {
        val capabilities = measureClient.getCapabilitiesAsync().await()
        return (DataType.ABSOLUTE_ELEVATION_STATS in capabilities.supportedDataTypesMeasure)
    }*/

    fun heartRateMeasureFlow() = callbackFlow {
        val callback = object : MeasureCallback {
            override fun onAvailabilityChanged(
                dataType: DeltaDataType<*, *>,
                availability: Availability
            ) {
                // Only send back DataTypeAvailability (not LocationAvailability)
                if (availability is DataTypeAvailability) {
                    trySendBlocking(MeasureMessage.MeasureAvailability(availability))
                }
            }

            override fun onDataReceived(data: DataPointContainer) {
                val heartRateBpm = data.getData(DataType.HEART_RATE_BPM)
                trySendBlocking(MeasureMessage.MeasureData(heartRateBpm))
            }
        }

        Log.d(TAG, "Registering for data")
        measureClient.registerMeasureCallback(DataType.HEART_RATE_BPM, callback)


        awaitClose {
            Log.d(TAG, "Unregistering for data")
            runBlocking {
                measureClient.unregisterMeasureCallbackAsync(DataType.HEART_RATE_BPM, callback)
                    .await()
            }
        }
    }
    fun elevationMeasureFlow() = callbackFlow {
        val callback = object : MeasureCallback {
            override fun onAvailabilityChanged(
                dataType: DeltaDataType<*, *>,
                availability: Availability
            ) {
                // Only send back DataTypeAvailability (not LocationAvailability)
                if (availability is DataTypeAvailability) {
                    trySendBlocking(MeasureMessage.MeasureAvailability(availability))
                }
            }

            override fun onDataReceived(data: DataPointContainer) {
                val elevationAbsolute = data.getData(DataType.ABSOLUTE_ELEVATION)
                trySendBlocking(MeasureMessage.MeasureData(elevationAbsolute))
            }
        }

        Log.d(TAG, "Registering for data")
        measureClient.registerMeasureCallback(DataType.ABSOLUTE_ELEVATION, callback)

        awaitClose {
            Log.d(TAG, "Unregistering for data")
            runBlocking {
                measureClient.unregisterMeasureCallbackAsync(DataType.ABSOLUTE_ELEVATION, callback)
                    .await()
            }
        }
    }
    fun paceMeasureFlow() = callbackFlow {
        val callback = object : MeasureCallback {
            override fun onAvailabilityChanged(
                dataType: DeltaDataType<*, *>,
                availability: Availability
            ) {
                // Only send back DataTypeAvailability (not LocationAvailability)
                if (availability is DataTypeAvailability) {
                    trySendBlocking(MeasureMessage.MeasureAvailability(availability))
                }
            }

            override fun onDataReceived(data: DataPointContainer) {
                val pace = data.getData(DataType.PACE)
                trySendBlocking(MeasureMessage.MeasureData(pace))
            }
        }

        Log.d(TAG, "Registering for data")
        measureClient.registerMeasureCallback(DataType.PACE, callback)

        awaitClose {
            Log.d(TAG, "Unregistering for data")
            runBlocking {
                measureClient.unregisterMeasureCallbackAsync(DataType.PACE, callback)
                    .await()
            }
        }
    }


}
sealed class MeasureMessage {
    class MeasureAvailability(val availability: DataTypeAvailability) : MeasureMessage()
    class MeasureData(val data: List<SampleDataPoint<Double>>) : MeasureMessage()
}

