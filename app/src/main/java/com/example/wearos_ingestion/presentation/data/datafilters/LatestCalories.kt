package com.example.wearos_ingestion.presentation.data.datafilters

import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.IntervalDataPoint

fun List<IntervalDataPoint<Double>>.latestCalories(): Double? {
    return this
        .filter { it.dataType == DataType.CALORIES }
        .maxByOrNull { it.startDurationFromBoot.toMillis() } // Find the maximum start duration
        ?.value

}
