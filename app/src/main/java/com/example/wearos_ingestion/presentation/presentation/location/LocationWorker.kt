package com.example.wearos_ingestion.presentation.presentation.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.wearos_ingestion.presentation.service.FirebaseHandler
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.tasks.await

@SuppressLint("MissingPermission")
class LocationWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val context = applicationContext
        val locationClient = LocationServices.getFusedLocationProviderClient(context)

        return try {
            val result = locationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                CancellationTokenSource().token
            ).await()

            result?.let { fetchedLocation ->
                val latitude = fetchedLocation.latitude
                val longitude = fetchedLocation.longitude

                // Send location data to Firebase
                val firebaseHandler = FirebaseHandler()
                firebaseHandler.sendLocationToFirebase(latitude, longitude)

                Log.d("LocationWorker", "Location sent to Firebase: ($latitude, $longitude)")
            }

            Result.success()
        } catch (e: Exception) {
            Log.e("LocationWorker", "Failed to get location", e)
            Result.failure()
        }
    }
}
