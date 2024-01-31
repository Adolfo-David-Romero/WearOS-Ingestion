package com.example.wearos_ingestion.presentation.model

import com.google.firebase.database.FirebaseDatabase
import android.util.Log
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SensorDataModel {
    private val TAG = "SensorDataModel"
    private val sensorData = StringBuilder()

    fun formatSensorData(sensorType: String, x: Float, y: Float, z: Float, timestamp: Long, duration: Long) {
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
//SensorDataViewModel acts as a bridge between UI (Compose components) and the SensorDataModel.
/*
class SensorDataViewModel : ViewModel() {
    private val sensorDataModel = SensorDataModel()

    fun formatSensorData(sensorType: String, x: Float, y: Float, z: Float, timestamp: Long, duration: Long) {
        sensorDataModel.formatSensorData(sensorType, x, y, z, timestamp, duration)
    }

    fun getFormattedSensorData(): String {
        return sensorDataModel.getFormattedSensorData()
    }

    fun sendDataToFirebase() {
        sensorDataModel.sendDataToFirebase()
    }
}*/
