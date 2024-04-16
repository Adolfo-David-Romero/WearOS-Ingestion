package com.example.wearos_ingestion.presentation.presentation.passive

import androidx.health.services.client.PassiveListenerService
import androidx.health.services.client.data.DataPointContainer
import androidx.health.services.client.data.DataType
import com.example.wearos_ingestion.presentation.data.repository.PassiveDataRepository
import com.example.wearos_ingestion.presentation.data.datafilters.latestCalories
import com.example.wearos_ingestion.presentation.data.datafilters.latestDailyCalories
import com.example.wearos_ingestion.presentation.data.datafilters.latestDailyDistance
import com.example.wearos_ingestion.presentation.data.datafilters.latestDailyFloors
import com.example.wearos_ingestion.presentation.data.datafilters.latestDailySteps
import com.example.wearos_ingestion.presentation.data.datafilters.latestDistance
import com.example.wearos_ingestion.presentation.data.datafilters.latestElevationGain
import com.example.wearos_ingestion.presentation.data.datafilters.latestFloors
import com.example.wearos_ingestion.presentation.data.datafilters.latestHeartRate
import com.example.wearos_ingestion.presentation.data.datafilters.latestSteps
import kotlinx.coroutines.runBlocking

class PassiveDataService : PassiveListenerService(){
    private val repository = PassiveDataRepository(this)

    override fun onNewDataPointsReceived(dataPoints: DataPointContainer) {
        runBlocking {
            dataPoints.getData(DataType.HEART_RATE_BPM).latestHeartRate()?.let {
                repository.storeLatestHeartRate(it)
            }
            // Handle elevation data
            dataPoints.getData(DataType.ELEVATION_GAIN).latestElevationGain()?.let {
                repository.storeLatestElevationGain(it)
            }
            dataPoints.getData(DataType.FLOORS).latestFloors()?.let {
                repository.storeLatestFloors(it)
            }
            dataPoints.getData(DataType.CALORIES).latestCalories()?.let {
                repository.storeLatestCalories(it)
            }
            dataPoints.getData(DataType.CALORIES_DAILY).latestDailyCalories()?.let {
                repository.storeLatestDailyCalories(it)
            }
            dataPoints.getData(DataType.DISTANCE_DAILY).latestDailyDistance()?.let {
                repository.storeLatestDailyDistance(it)
            }
            dataPoints.getData(DataType.DISTANCE).latestDistance()?.let {
                repository.storeLatestDistance(it)
            }
            dataPoints.getData(DataType.STEPS_DAILY).latestDailySteps()?.let {
                repository.storeLatestDailySteps(it)
            }
            dataPoints.getData(DataType.STEPS).latestSteps()?.let {
                repository.storeLatestSteps(it)
            }
            dataPoints.getData(DataType.FLOORS_DAILY).latestDailyFloors()?.let {
                repository.storeLatestDailyFloors(it)
            }
        }
    }
}