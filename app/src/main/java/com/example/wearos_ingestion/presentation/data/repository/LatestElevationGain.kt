package com.example.wearos_ingestion.presentation.data.repository

import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.IntervalDataPoint

// Extension function to find the latest elevation value
fun List<IntervalDataPoint<Double>>.latestElevationGain(): Double? {
    return this
        .filter { it.dataType == DataType.ELEVATION_GAIN }
        .map { it.value }
        .firstOrNull()
}
