package com.example.wearos_ingestion.presentation.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "passive_data")
class PassiveDataRepository(private val context: Context) {
    val passiveDataEnabled: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[PASSIVE_DATA_ENABLED] ?: false
    }

    suspend fun setPassiveDataEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PASSIVE_DATA_ENABLED] = enabled
        }
    }

    val latestHeartRate: Flow<Double> = context.dataStore.data.map { prefs ->
        prefs[LATEST_HEART_RATE] ?: 0.0
    }
    val latestElevationGain: Flow<Double> = context.dataStore.data.map { prefs ->
        prefs[LATEST_ELEVATION_GAIN] ?: 0.0
    }
    val latestFloors: Flow<Double> = context.dataStore.data.map { prefs ->
        prefs[LATEST_FLOORS] ?: 0.0
    }
    val latestCalories: Flow<Double> = context.dataStore.data.map { prefs ->
        prefs[LATEST_CALORIES] ?: 0.0
    }
    val latestDailyCalories: Flow<Double> = context.dataStore.data.map { prefs ->
        prefs[LATEST_DAILY_CALORIES] ?: 0.0
    }
    val latestDailyDistance: Flow<Double> = context.dataStore.data.map { prefs ->
        prefs[LATEST_DAILY_DISTANCE] ?: 0.0
    }
    val latestDistance: Flow<Double> = context.dataStore.data.map { prefs ->
        prefs[LATEST_DISTANCE] ?: 0.0
    }
    val latestDailySteps: Flow<Double> = context.dataStore.data.map { prefs ->
        prefs[LATEST_DAILY_STEPS] ?: 0.0
    }
    val latestSteps: Flow<Double> = context.dataStore.data.map { prefs ->
        prefs[LATEST_STEPS] ?: 0.0
    }
    val latestDailyFloors: Flow<Double> = context.dataStore.data.map { prefs ->
        prefs[LATEST_DAILY_FLOORS] ?: 0.0
    }
    val latestDailyElevationGain: Flow<Double> = context.dataStore.data.map { prefs ->
        prefs[LATEST_DAILY_ELEVATION_GAIN] ?: 0.0
    }



    suspend fun storeLatestHeartRate(heartRate: Double) {
        context.dataStore.edit { prefs ->
            prefs[LATEST_HEART_RATE] = heartRate
        }
    }
    suspend fun storeLatestElevationGain(elevation: Double) {
        context.dataStore.edit { prefs ->
            prefs[LATEST_ELEVATION_GAIN] = elevation
        }
    }
    suspend fun storeLatestFloors(floors: Double) {
        context.dataStore.edit { prefs ->
            prefs[LATEST_FLOORS] = floors
        }
    }
    suspend fun storeLatestCalories(calories: Double) {
        context.dataStore.edit { prefs ->
            prefs[LATEST_CALORIES] = calories
        }
    }
    suspend fun storeLatestDailyCalories(dailyCalories: Double) {
        context.dataStore.edit { prefs ->
            prefs[LATEST_DAILY_CALORIES] = dailyCalories
        }
    }

    suspend fun storeLatestDailyDistance(dailyDistance: Double) {
        context.dataStore.edit { prefs ->
            prefs[LATEST_DAILY_DISTANCE] = dailyDistance
        }
    }
    suspend fun storeLatestDistance(distance: Double) {
        context.dataStore.edit { prefs ->
            prefs[LATEST_DISTANCE] = distance
        }
    }
    suspend fun storeLatestDailySteps(dailySteps: Long) {
        context.dataStore.edit { prefs ->
            prefs[LATEST_DAILY_STEPS] = dailySteps.toDouble()
        }
    }
    suspend fun storeLatestSteps(steps: Long) {
        context.dataStore.edit { prefs ->
            prefs[LATEST_STEPS] = steps.toDouble()
        }
    }
    suspend fun storeLatestDailyFloors(dailyFloors: Double) {
        context.dataStore.edit { prefs ->
            prefs[LATEST_DAILY_FLOORS] = dailyFloors
        }
    }

    companion object {
        private val PASSIVE_DATA_ENABLED = booleanPreferencesKey("passive_data_enabled")
        private val LATEST_HEART_RATE = doublePreferencesKey("latest_heart_rate")
        private val LATEST_ELEVATION_GAIN = doublePreferencesKey("latest_elevation_gain")
        private val LATEST_FLOORS = doublePreferencesKey("latest_floors")
        private val LATEST_CALORIES = doublePreferencesKey("latest_calories")
        private val LATEST_DAILY_CALORIES = doublePreferencesKey("latest_daily_calories")
        private val LATEST_DAILY_DISTANCE = doublePreferencesKey("latest_daily_distance")
        private val LATEST_DISTANCE = doublePreferencesKey("latest_distance")
        private val LATEST_DAILY_STEPS = doublePreferencesKey("latest_daily_steps")
        private val LATEST_STEPS = doublePreferencesKey("latest_steps")
        private val LATEST_DAILY_FLOORS = doublePreferencesKey("latest_daily_floors")
        private val LATEST_DAILY_ELEVATION_GAIN = doublePreferencesKey("latest_daily_elevation_gain")


    }
}