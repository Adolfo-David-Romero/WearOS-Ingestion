/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.wearos_ingestion.presentation

import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.health.connect.datatypes.ExerciseRoute
import android.os.Bundle

import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import com.example.wearos_ingestion.R
import com.example.wearos_ingestion.presentation.model.SensorDataModel
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.ui.tooling.preview.WearPreviewDevices
import com.example.passivedatacompose.presentation.PassiveDataApp
import com.example.wearos_ingestion.presentation.theme.WearOSIngestionTheme
import com.google.android.gms.location.FusedLocationProviderClient


class MainActivity : ComponentActivity() {

    /*    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var gyroscope: Sensor? = null
    private var heartRate: Sensor? = null

    *//*    private lateinit var sensorDataTextView: TextView
        private lateinit var sendDataButton: Button*//*
    private lateinit var sensorModel: SensorDataModel*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val healthServicesRepository = (application as MainApplication).healthServicesRepository
        val passiveDataRepository = (application as MainApplication).passiveDataRepository

        setContent {
            PassiveDataApp(
                healthServicesRepository = healthServicesRepository,
                passiveDataRepository = passiveDataRepository
            )
        }

        /*        // Initialize the sensor manager
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        // Initialize the accelerometer sensor
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        // Initialize the gyroscope sensor
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

        //
        heartRate = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE)

        // Check if the gyroscope is available
        if (gyroscope == null) {
            Log.e("Sensor Error", "Gyroscope sensor not available on this device.")
        } else {
            Log.d("Sensor Info", "Gyroscope sensor available: $gyroscope")
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL)
        }

        // Check if the accelerometer is available
        if (accelerometer == null) {
            // Handle the case where the accelerometer sensor is not available
            Log.e("Sensor Error", "Accelerometer sensor not available on this device.")
        } else {
            // Log information about the accelerometer
            Log.d("Sensor Info", "Accelerometer sensor available: $accelerometer")
            // Continue with your sensor registration logic
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        }
        // Check if the heart rate is available
        if (heartRate == null) {
            // Handle the case where the accelerometer sensor is not available
            Log.e("Sensor Error", "Heart Rate sensor not available on this device.")
        } else {
            // Log information about the accelerometer
            Log.d("Sensor Info", "Heart Rate sensor available: $heartRate")
            // Continue with your sensor registration logic
            sensorManager.registerListener(this, heartRate, SensorManager.SENSOR_DELAY_NORMAL)
        }
        // Initialize the sensor model
        sensorModel = SensorDataModel()

        setContent {
            WearOSDataIngestion(sensorDataModel = sensorModel)
        }
    }

    override fun onResume() {
        super.onResume()
        // Register the sensors if available
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
            Log.d("Sensor Info", "${it.name} sensor listener registered")
        }

        gyroscope?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
            Log.d("Sensor Info", "${it.name} sensor listener registered")
        }

        heartRate?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
            Log.d("Sensor Info", "${it.name} sensor listener registered")
        }
    }

    override fun onPause() {
        super.onPause()
        // Unregister the sensors if available
        accelerometer?.let {
            sensorManager.unregisterListener(this, it)
            Log.d("Sensor Info", "${it.name} sensor listener unregistered")
        }

        gyroscope?.let {
            sensorManager.unregisterListener(this, it)
            Log.d("Sensor Info", "${it.name} sensor listener unregistered")
        }

        heartRate?.let {
            sensorManager.unregisterListener(this, it)
            Log.d("Sensor Info", "${it.name} sensor listener unregistered")
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> {
                // Handle accelerometer data
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]
                val timestamp = System.currentTimeMillis()
                val durationMillis = (System.nanoTime() - event.timestamp) / 1_000_000
                sensorModel.formatSensorData("Accelerometer", x, y, z, timestamp, durationMillis)
            }

            Sensor.TYPE_GYROSCOPE -> {
                // Handle gyroscope data
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]
                val timestamp = System.currentTimeMillis()
                val durationMillis = (System.nanoTime() - event.timestamp) / 1_000_000
                sensorModel.formatSensorData("Gyroscope", x, y, z, timestamp, durationMillis)
            }

            Sensor.TYPE_HEART_RATE -> {
                // Handle heart rate data
                val heartRate = event.values[0]
                val timestamp = System.currentTimeMillis()
                val durationMillis = (System.nanoTime() - event.timestamp) / 1_000_000
                sensorModel.formatSensorData(
                    "Heart Rate (BPM)",
                    heartRate,
                    0f,
                    0f,
                    timestamp,
                    durationMillis
                )
            }
        }
        // Update UI with raw sensor data
        updateSensorDataOnUI()
    }*/


        /*    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not needed for this example
    }

    private fun updateSensorDataOnUI() {
        // Update the TextView with formatted sensor data
        // You can replace this with a Compose UI update if needed
        Log.d("UI Update", "Sensor data updated on UI: ${sensorModel.getFormattedSensorData()}")
    }

    @Composable
    fun WearOSDataIngestion(sensorDataModel: SensorDataModel) {
        ScalingLazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item { SendDataButton(onClick = { sensorDataModel.sendDataToFirebase() }) }
            item { SensorDataView(sensorData = sensorDataModel.getFormattedSensorData()) }

        }
    }

    @Composable
    fun SensorDataView(sensorData: String) {
        Text(
            text = "Sensor Data:\n$sensorData",
            style = MaterialTheme.typography.body1,
            color = Color.Black
        )
    }

    @Composable
    fun SendDataButton(onClick: () -> Unit) {
        val keyboardController = LocalSoftwareKeyboardController.current
        Button(
            onClick = {
                onClick()
                keyboardController?.hide()
            },
            modifier = Modifier
                .padding(top = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Send Data"
            )
            Spacer(modifier = Modifier.width(8.dp))
            //Text("Send Data")
        }
    }

    @Preview
    @Composable
    fun AppPreview() {
        WearOSDataIngestion(sensorDataModel = sensorModel)
    }*/


    }
}









