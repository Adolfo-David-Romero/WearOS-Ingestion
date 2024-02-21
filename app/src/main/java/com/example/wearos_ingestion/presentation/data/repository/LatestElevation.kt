package com.example.wearos_ingestion.presentation.data.repository

import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.SampleDataPoint

// Extension function to find the latest elevation value
fun List<SampleDataPoint<Double>>.latestElevation(): Double? {
    return this
        .filter { it.dataType == DataType.ABSOLUTE_ELEVATION }
        // Optionally, filter based on accuracy or other criteria
        .filter { it.value > 0 } // For elevation, you might want to filter out invalid values
        .maxByOrNull { it.timeDurationFromBoot }?.value
}
