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
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import com.example.wearos_ingestion.R
import com.example.wearos_ingestion.presentation.model.SensorDataModel
import android.util.Log


class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private lateinit var sensorDataTextView: TextView
    private lateinit var sendDataButton: Button
    private lateinit var sensorModel: SensorDataModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the sensor manager
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        // Initialize the accelerometer sensor
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

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

        // Initialize UI components
        sensorDataTextView = findViewById(R.id.sensorDataTextView)
        sendDataButton = findViewById(R.id.sendDataButton)

        // Initialize the sensor model
        sensorModel = SensorDataModel()

        // Set up button click listener
        sendDataButton.setOnClickListener {
            Log.d("Button Click", "Send Data button clicked")
            // Send sensor data to Firebase and update UI
            sensorModel.sendDataToFirebase()
            updateSensorDataOnUI()
        }
    }

    override fun onResume() {
        super.onResume()
        // Register the accelerometer sensor listener if available
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
            Log.d("Sensor Info", "Accelerometer sensor listener registered")
        }
    }

    override fun onPause() {
        super.onPause()
        // Unregister the accelerometer sensor listener if available
        accelerometer?.let {
            sensorManager.unregisterListener(this)
            Log.d("Sensor Info", "Accelerometer sensor listener unregistered")
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val currentTimeNano = System.nanoTime()
            val durationNano = currentTimeNano - event.timestamp
            val durationMillis = durationNano / 1_000_000

            // Log accelerometer data and duration
            Log.d("Sensor Data", "X: $x, Y: $y, Z: $z, Duration: $durationMillis ms")

            val timestamp = System.currentTimeMillis()

            // Use the model to handle sensor data
            sensorModel.formatSensorData(x, y, z, timestamp, durationMillis)

            // Update UI with raw sensor data
            updateSensorDataOnUI()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not needed for this example
    }

    private fun updateSensorDataOnUI() {
        // Update the TextView with formatted sensor data
        sensorDataTextView.text = "Sensor Data:\n${sensorModel.getFormattedSensorData()}"
        Log.d("UI Update", "Sensor data updated on UI")
    }
}