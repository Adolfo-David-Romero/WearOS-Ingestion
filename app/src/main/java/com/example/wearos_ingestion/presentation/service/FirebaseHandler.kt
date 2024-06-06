package com.example.wearos_ingestion.presentation.service

import android.util.Log
import com.google.firebase.database.FirebaseDatabase

class FirebaseHandler {
    private val TAG = "FirebaseHandler"

    fun sendLocationToFirebase(latitude: Double, longitude: Double) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("locations")
        val key = databaseReference.push().key
        key?.let {
            val locationData = mapOf(
                "latitude" to latitude,
                "longitude" to longitude,
                "timestamp" to System.currentTimeMillis()
            )
            databaseReference.child(key).setValue(locationData)

            // Log that data has been sent to Firebase
            Log.d(TAG, "Location Data Sent to Firebase. Key: $key")
        }
    }
}