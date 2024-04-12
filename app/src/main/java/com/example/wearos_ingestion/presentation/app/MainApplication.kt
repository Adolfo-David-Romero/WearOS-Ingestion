package com.example.wearos_ingestion.presentation.app

import android.app.Application
import com.example.wearos_ingestion.presentation.data.repository.HealthServicesRepository
import com.example.wearos_ingestion.presentation.data.repository.PassiveDataRepository

const val TAG = "Passive Data Sample"
const val PERMISSION = android.Manifest.permission.BODY_SENSORS
const val LOCATIONPERMISSION = android.Manifest.permission.ACCESS_FINE_LOCATION

class MainApplication : Application() {
    val healthServicesRepository by lazy { HealthServicesRepository(this) }
    val passiveDataRepository by lazy { PassiveDataRepository(this) }
}