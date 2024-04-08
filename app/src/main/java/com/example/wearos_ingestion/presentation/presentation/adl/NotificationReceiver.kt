package com.example.wearos_ingestion.presentation.presentation.adl

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.wearos_ingestion.R

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            "dinner_channel",
            "Dinner Time",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(context, "dinner_channel")
            .setContentTitle("Dinner Time")
            .setContentText("It's time for dinner!")
            .build()

        notificationManager.notify(0, notification)
    }
}
