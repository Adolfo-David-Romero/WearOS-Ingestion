package com.example.wearos_ingestion.presentation.model

import com.google.firebase.database.FirebaseDatabase
import android.util.Log

class SensorDataModel {
    private val TAG = "SensorDataModel"
    private val sensorData = StringBuilder()

    fun formatSensorData(x: Float, y: Float, z: Float, timestamp: Long, duration: Long) {
        val formattedData =
            "Type: Accelerometer, Raw Data: ($x, $y, $z), Time: $timestamp, Duration: $duration\n"
        sensorData.append(formattedData)

        // Log the formatted data for debugging
        Log.d(TAG, "Formatted Sensor Data: $formattedData")
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

    fun sendDataToFirebase() {
        // Replace with your Firebase database reference
        val databaseReference = FirebaseDatabase.getInstance().getReference("sensorData")
        //val sensorDataRef = database.child("sensorData")

        // Push sensor data to Firebase
        val key = databaseReference.push().key
        key?.let {
            databaseReference.child(key).setValue(sensorData.toString())

            // Log that data has been sent to Firebase
            Log.d(TAG, "Sensor Data Sent to Firebase. Key: $key")
        }

        // Clear sensor data after sending to Firebase
        clearSensorData()
    }
}