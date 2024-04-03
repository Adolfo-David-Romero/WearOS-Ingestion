/*
package com.example.wearos_ingestion.presentation.presentation.adl

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.wearos_ingestion.R
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DinnerTimeScreen()
        }
    }
}

@Composable
fun DinnerTimeScreen() {
    val context = LocalContext.current
    var dinnerTime by remember { mutableStateOf("") }

    Surface(color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = dinnerTime,
                onValueChange = { dinnerTime = it },
                label = { Text("Enter Dinner Time (HH:MM)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    saveDinnerTime(context, dinnerTime)
                    scheduleNotification(context, dinnerTime)
                }
            ) {
                Text("Set Dinner Time")
            }
        }
    }
}

fun saveDinnerTime(context: Context, time: String) {
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("DinnerTime", time)
    editor.apply()
}

fun scheduleNotification(context: Context, time: String) {
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val dinnerTime = sharedPreferences.getString("DinnerTime", "") ?: return

    val calendar = Calendar.getInstance()
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
    val currentMinute = calendar.get(Calendar.MINUTE)

    val dinnerTimeSplit = dinnerTime.split(":")
    val dinnerHour = dinnerTimeSplit[0].toInt()
    val dinnerMinute = dinnerTimeSplit[1].toInt()

    calendar.set(Calendar.HOUR_OF_DAY, dinnerHour)
    calendar.set(Calendar.MINUTE, dinnerMinute)
    calendar.set(Calendar.SECOND, 0)

    if (calendar.timeInMillis < System.currentTimeMillis()) {
        calendar.add(Calendar.DAY_OF_MONTH, 1)
    }

    val intent = Intent(context, NotificationReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

    createNotificationChannel(context)
}

fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Dinner Reminder"
        val descriptionText = "Reminds you about dinner time"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("dinner_reminder", name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = NotificationManagerCompat.from(context)
        val notification = NotificationCompat.Builder(context, "dinner_reminder")
            .setContentTitle("Dinner Time!")
            .setContentText("It's time to have dinner!")
            .setSmallIcon(R.drawable.ic_dinner)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(1, notification)
    }
}*/
