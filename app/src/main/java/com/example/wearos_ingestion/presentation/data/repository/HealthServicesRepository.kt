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
import com.example.wearos_ingestion.presentation.presentation.passive.PassiveDataService
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

    /** -Passive Client- **/
    val passiveMonitoringClient = healthServicesClient.passiveMonitoringClient
    private val dataTypes = setOf(
        DataType.HEART_RATE_BPM,
        DataType.ELEVATION_GAIN,
        DataType.FLOORS,
        DataType.CALORIES,
        DataType.CALORIES_DAILY,
        DataType.DISTANCE_DAILY,
        DataType.DISTANCE,
        DataType.STEPS_DAILY,
        DataType.STEPS,
        DataType.FLOORS_DAILY,
        //DataType.ELEVATION_GAIN_TOTAL,
        //DataType.ELEVATION_LOSS,
    )

    private val passiveListenerConfig = PassiveListenerConfig(
        dataTypes = dataTypes,
        shouldUserActivityInfoBeRequested = false,
        dailyGoals = setOf(),
        healthEventTypes = setOf()
    )

    /** Has Datatype Capability **/
    suspend fun hasPassiveHeartRateCapability(): Boolean {
        val capabilities = passiveMonitoringClient.getCapabilitiesAsync().await()
        return DataType.HEART_RATE_BPM in capabilities.supportedDataTypesPassiveMonitoring
    }

    suspend fun hasPassiveElevationGainCapability(): Boolean {
        val capabilities = passiveMonitoringClient.getCapabilitiesAsync().await()
        return DataType.ELEVATION_GAIN in capabilities.supportedDataTypesPassiveMonitoring
    }

    suspend fun hasPassiveFloorsCapability(): Boolean {
        val capabilities = passiveMonitoringClient.getCapabilitiesAsync().await()
        return DataType.FLOORS in capabilities.supportedDataTypesPassiveMonitoring
    }

    suspend fun hasPassiveCaloriesCapability(): Boolean {
        val capabilities = passiveMonitoringClient.getCapabilitiesAsync().await()
        return DataType.CALORIES in capabilities.supportedDataTypesPassiveMonitoring
    }
    suspend fun hasPassiveDailyCaloriesCapability(): Boolean {
        val capabilities = passiveMonitoringClient.getCapabilitiesAsync().await()
        return DataType.CALORIES_DAILY in capabilities.supportedDataTypesPassiveMonitoring
    }

    suspend fun hasPassiveDailyDistanceCapability(): Boolean {
        val capabilities = passiveMonitoringClient.getCapabilitiesAsync().await()
        return DataType.DISTANCE_DAILY in capabilities.supportedDataTypesPassiveMonitoring
    }

    suspend fun hasPassiveDistanceCapability(): Boolean {
        val capabilities = passiveMonitoringClient.getCapabilitiesAsync().await()
        return DataType.DISTANCE in capabilities.supportedDataTypesPassiveMonitoring
    }

    suspend fun hasPassiveDailyStepsCapability(): Boolean {
        val capabilities = passiveMonitoringClient.getCapabilitiesAsync().await()
        return DataType.STEPS_DAILY in capabilities.supportedDataTypesPassiveMonitoring
    }
    suspend fun hasPassiveStepsCapability(): Boolean {
        val capabilities = passiveMonitoringClient.getCapabilitiesAsync().await()
        return DataType.STEPS in capabilities.supportedDataTypesPassiveMonitoring
    }

    suspend fun hasPassiveDailyFloorsCapability(): Boolean {
        val capabilities = passiveMonitoringClient.getCapabilitiesAsync().await()
        return DataType.FLOORS_DAILY in capabilities.supportedDataTypesPassiveMonitoring
    }

    suspend fun hasPassiveElevationLossCapability(): Boolean {
        val capabilities = passiveMonitoringClient.getCapabilitiesAsync().await()
        return DataType.ELEVATION_LOSS in capabilities.supportedDataTypesPassiveMonitoring
    }


    /** Register/Unregister Datatype **/
    suspend fun registerForHeartRateData() {
        Log.i(TAG, "Heart Rate Data: Registering listener")
        passiveMonitoringClient.setPassiveListenerServiceAsync(
            PassiveDataService::class.java,
            passiveListenerConfig
        ).await()
    }
    suspend fun unregisterForHeartRateData() {
        Log.i(TAG, "Heart Rate Data: Unregistering listeners")
        passiveMonitoringClient.clearPassiveListenerServiceAsync().await()
    }
    suspend fun registerForElevationGainData() {
        Log.i(TAG, "Elevation Gain Data: Registering listener")
        passiveMonitoringClient.setPassiveListenerServiceAsync(
            PassiveDataService::class.java,
            passiveListenerConfig
        ).await()
    }
    suspend fun unregisterForElevationGainData() {
        Log.i(TAG, "Elevation Gain Data: Unregistering listeners")
        passiveMonitoringClient.clearPassiveListenerServiceAsync().await()
    }
    suspend fun registerForFloorsData() {
        Log.i(TAG, "Floors Data: Registering listener")
        passiveMonitoringClient.setPassiveListenerServiceAsync(
            PassiveDataService::class.java,
            passiveListenerConfig
        ).await()
    }
    suspend fun unregisterForFloorsData() {
        Log.i(TAG, "Floors Data: Unregistering listeners")
        passiveMonitoringClient.clearPassiveListenerServiceAsync().await()
    }
    suspend fun registerForCaloriesData() {
        Log.i(TAG, "Calories Data: Registering listener")
        passiveMonitoringClient.setPassiveListenerServiceAsync(
            PassiveDataService::class.java,
            passiveListenerConfig
        ).await()
    }
    suspend fun unregisterForCaloriesData() {
        Log.i(TAG, "Calories Data: Unregistering listeners")
        passiveMonitoringClient.clearPassiveListenerServiceAsync().await()
    }

    suspend fun registerForDailyCaloriesData() {
        Log.i(TAG, "Calories Data: Registering listener")
        passiveMonitoringClient.setPassiveListenerServiceAsync(
            PassiveDataService::class.java,
            passiveListenerConfig
        ).await()
    }

    suspend fun unregisterForDailyCaloriesData() {
        Log.i(TAG, "Calories Data: Unregistering listeners")
        passiveMonitoringClient.clearPassiveListenerServiceAsync().await()
    }
    suspend fun registerForDailyDistanceData() {
        Log.i(TAG, "Daily Distance Data: Registering listener")
        passiveMonitoringClient.setPassiveListenerServiceAsync(
            PassiveDataService::class.java,
            passiveListenerConfig
        ).await()
    }
    suspend fun unregisterForDailyDistanceData() {
        Log.i(TAG, "Daily Distance Data: Unregistering listeners")
        passiveMonitoringClient.clearPassiveListenerServiceAsync().await()
    }
    suspend fun registerForDistanceData() {
        Log.i(TAG, "Distance Data: Registering listener")
        passiveMonitoringClient.setPassiveListenerServiceAsync(
            PassiveDataService::class.java,
            passiveListenerConfig
        ).await()
    }
    suspend fun unregisterForDistanceData() {
        Log.i(TAG, "Distance Data: Unregistering listeners")
        passiveMonitoringClient.clearPassiveListenerServiceAsync().await()
    }
    suspend fun registerForDailyStepsData() {
        Log.i(TAG, "Daily Steps Data: Registering listener")
        passiveMonitoringClient.setPassiveListenerServiceAsync(
            PassiveDataService::class.java,
            passiveListenerConfig
        ).await()
    }
    suspend fun unregisterForDailyStepsData() {
        Log.i(TAG, "Daily Steps Data: Unregistering listeners")
        passiveMonitoringClient.clearPassiveListenerServiceAsync().await()
    }
    suspend fun registerForStepsData() {
        Log.i(TAG, "Steps Data: Registering listener")
        passiveMonitoringClient.setPassiveListenerServiceAsync(
            PassiveDataService::class.java,
            passiveListenerConfig
        ).await()
    }
    suspend fun unregisterForStepsData() {
        Log.i(TAG, "Steps Data: Unregistering listeners")
        passiveMonitoringClient.clearPassiveListenerServiceAsync().await()
    }
    suspend fun registerForDailyFloorsData() {
        Log.i(TAG, "Daily Floors Data: Registering listener")
        passiveMonitoringClient.setPassiveListenerServiceAsync(
            PassiveDataService::class.java,
            passiveListenerConfig
        ).await()
    }
    suspend fun unregisterForDailyFloorsData() {
        Log.i(TAG, "Daily Floors Data: Unregistering listeners")
        passiveMonitoringClient.clearPassiveListenerServiceAsync().await()
    }

    /** -Measure Client- **/
    val measureClient = healthServicesClient.measureClient

    suspend fun hasMeasuredHeartRateCapability(): Boolean {
        val capabilities = measureClient.getCapabilitiesAsync().await()
        return (DataType.HEART_RATE_BPM in capabilities.supportedDataTypesMeasure)
    }

    suspend fun hasMeasuredElevationCapability(): Boolean {
        val capabilities = measureClient.getCapabilitiesAsync().await()
        return (DataType.ABSOLUTE_ELEVATION in capabilities.supportedDataTypesMeasure)
    }

    fun heartRateMeasureFlow() = callbackFlow {
        val callback = object : MeasureCallback {
            override fun onAvailabilityChanged(
                dataType: DeltaDataType<*, *>,
                availability: Availability
            ) {
                // Only send back DataTypeAvailability (not LocationAvailability)
                if (availability is DataTypeAvailability) {
                    trySendBlocking(MeasureMessage.MeasureAvailability(availability))
                    Log.d(TAG, "💓 hr avail: $availability")
                }
            }

            override fun onDataReceived(data: DataPointContainer) {
                val heartRateBpm = data.getData(DataType.HEART_RATE_BPM)
                Log.d(TAG, "💓 Received heart rate: ${heartRateBpm.first().value}")
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
                Log.i(
                    "elevation",
                    "💓 Received ABSOLUTE_ELEVATION: ${elevationAbsolute.first().value}"
                )
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

    fun v02MaxMeasureFlow() = callbackFlow {
        val callback = object : MeasureCallback {
            override fun onAvailabilityChanged(
                dataType: DeltaDataType<*, *>,
                availability: Availability
            ) {
                // Only send back DataTypeAvailability (not LocationAvailability)
                if (availability is DataTypeAvailability) {
                    trySendBlocking(MeasureMessage.MeasureAvailability(availability))
                    Log.d(TAG, "💓 max v 02 avail: $availability")
                }
            }

            override fun onDataReceived(data: DataPointContainer) {
                val v02Max = data.getData(DataType.VO2_MAX)
                Log.d(TAG, "💓 Received max v 02: ${v02Max.first().value}")
                trySendBlocking(MeasureMessage.MeasureData(v02Max))
            }
        }

        Log.d(TAG, "Registering for data")
        measureClient.registerMeasureCallback(DataType.VO2_MAX, callback)

        awaitClose {
            Log.d(TAG, "Unregistering for data")
            runBlocking {
                measureClient.unregisterMeasureCallbackAsync(DataType.VO2_MAX, callback)
                    .await()
            }
        }
    }


}

sealed class MeasureMessage {
    class MeasureAvailability(val availability: DataTypeAvailability) : MeasureMessage()
    class MeasureData(val data: List<SampleDataPoint<Double>>) : MeasureMessage()


}

