package com.example.wearos_ingestion.presentation.service

import android.util.Log
import com.google.firebase.database.FirebaseDatabase

class FirebaseHandler {
    private val TAG = "FirebaseHandler"

     fun sendDataToFirebase(sensorData: String) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("sensorData")
        val key = databaseReference.push().key
        key?.let {
            databaseReference.child(key).setValue(sensorData)

            // Log that data has been sent to Firebase
            Log.d(TAG, "Sensor Data Sent to Firebase. Key: $key")
        }
    }
}