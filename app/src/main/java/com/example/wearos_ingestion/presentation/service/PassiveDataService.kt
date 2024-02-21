package com.example.wearos_ingestion.presentation.service

import androidx.health.services.client.PassiveListenerService
import androidx.health.services.client.data.DataPointContainer
import androidx.health.services.client.data.DataType
import com.example.wearos_ingestion.presentation.data.repository.PassiveDataRepository
import com.example.wearos_ingestion.presentation.data.repository.latestElevation
import com.example.wearos_ingestion.presentation.data.repository.latestHeartRate
import kotlinx.coroutines.runBlocking

class PassiveDataService : PassiveListenerService(){
    private val repository = PassiveDataRepository(this)

    override fun onNewDataPointsReceived(dataPoints: DataPointContainer) {
        runBlocking {
            dataPoints.getData(DataType.HEART_RATE_BPM).latestHeartRate()?.let {
                repository.storeLatestHeartRate(it)
            }
            // Handle elevation data
            dataPoints.getData(DataType.ABSOLUTE_ELEVATION).latestElevation()?.let {
                repository.storeLatestElevation(it)
            }
        }
    }
}