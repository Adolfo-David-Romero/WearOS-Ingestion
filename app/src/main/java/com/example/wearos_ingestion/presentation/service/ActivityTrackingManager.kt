package com.example.wearos_ingestion.presentation.service

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class ActivityTrackingManager {
    // Track the state of activity tracking for each activity
    var isWalkingTrackingEnabled by mutableStateOf(true)
        private set

    // You can add more activity tracking states here as needed

    // Function to toggle tracking for a specific activity
    fun toggleTracking(activity: Activity, enabled: Boolean) {
        when (activity) {
            Activity.WALKING -> isWalkingTrackingEnabled = enabled
            // Add other activities as needed
        }
    }
}

enum class Activity {
    WALKING,
    // Add other activities as needed
}