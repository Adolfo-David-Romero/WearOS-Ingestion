package com.example.wearos_ingestion.presentation.data.model

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SensorDataModel {
    private val TAG = "SensorDataModel"
    private val sensorData = StringBuilder()

    fun formatSensorData(
        sensorType: String,
        x: Float,
        y: Float,
        z: Float,
        timestamp: Long,
        duration: Long
    ) {
        val formattedTime = formatTimestamp(timestamp)
        val formattedData = """
       
            Sensor Type: $sensorType,
            Sensor Data: ($x, $y, $z),
            Time: $formattedTime,
            Duration: $duration ms
        """.trimIndent()
        sensorData.append(formattedData)

        // Log the formatted data for debugging
        Log.d(TAG, "Formatted Sensor Data: $formattedData")
    }

    private fun formatTimestamp(timestamp: Long): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return simpleDateFormat.format(Date(timestamp))
    }

    fun getFormattedSensorData(): String {
        // Log the sensor data for debugging
        Log.d(TAG, "Retrieved Formatted Sensor Data: ${sensorData.toString()}")
        return sensorData.toString()
    }

    fun clearSensorData() {
        sensorData.clear()

        // Log that sensor data has been cleared
        Log.d(TAG, "Sensor Data Cleared")
    }
}
