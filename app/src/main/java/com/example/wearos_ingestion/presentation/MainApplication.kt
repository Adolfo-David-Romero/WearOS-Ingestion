package com.example.wearos_ingestion.presentation

import android.app.Application
import com.example.wearos_ingestion.presentation.data.repository.HealthServicesRepository
import com.example.wearos_ingestion.presentation.data.repository.PassiveDataRepository

const val TAG = "Passive Data Sample"
const val PERMISSION = android.Manifest.permission.BODY_SENSORS

class MainApplication : Application() {
    val healthServicesRepository by lazy { HealthServicesRepository(this) }
    val passiveDataRepository by lazy { PassiveDataRepository(this) }
}