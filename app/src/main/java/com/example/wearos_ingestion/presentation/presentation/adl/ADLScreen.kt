package com.example.wearos_ingestion.presentation.presentation.adl

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import com.example.wearos_ingestion.presentation.presentation.home.BackNavigationButton
import com.example.wearos_ingestion.presentation.theme.IngestionAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@Composable
fun ADLScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    var selectedHour by remember { mutableStateOf(20) }
    var selectedMinute by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ScalingLazyColumn(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            item{ BackNavigationButton(navController = navController) }
            item {
                OutlinedTextField(
                    value = selectedHour.toString(),
                    onValueChange = { selectedHour = it.toIntOrNull() ?: 0 },
                    label = { Text(text = "Hour (0-23)") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )
            }
            item {
                OutlinedTextField(
                    value = selectedMinute.toString(),
                    onValueChange = { selectedMinute = it.toIntOrNull() ?: 0 },
                    label = { Text(text = "Minute (0-59)") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Button(onClick = {
                    // Save the selected time
                    val calendar = Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, selectedHour)
                        set(Calendar.MINUTE, selectedMinute)
                    }

                    val intent = Intent(context, NotificationReceiver::class.java)
                    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                        PendingIntent.FLAG_IMMUTABLE)

                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
                }) {
                    Text(text = "Save", fontSize = 18.sp)
                }
            }
        }
    }
}

@ExperimentalPermissionsApi
@Preview(
    device = Devices.WEAR_OS_SMALL_ROUND,
    showBackground = false,
    showSystemUi = true
)
@Composable
fun ADLScreenPreview() {
    val navController = rememberNavController() // Create a NavController instance
    IngestionAppTheme {
        ADLScreen(navController)
    }
}