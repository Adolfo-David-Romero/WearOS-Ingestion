package com.example.wearos_ingestion.presentation.presentation.activityrecognition

import android.annotation.SuppressLint
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import com.example.wearos_ingestion.presentation.service.CUSTOM_INTENT_USER_ACTION
import com.example.wearos_ingestion.presentation.service.UserActivityBroadcastReceiver
import com.example.wearos_ingestion.presentation.service.UserActivityTransitionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import android.Manifest
import android.os.Build
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.wearos_ingestion.presentation.app.PERMISSION
import com.example.wearos_ingestion.presentation.presentation.passive.PassiveAppScreen
import com.example.wearos_ingestion.presentation.theme.IngestionAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus


const val CUSTOM_INTENT_USER_ACTION = "USER-ACTIVITY-DETECTION-INTENT-ACTION"
@SuppressLint("MissingPermission")
/*@Sample(
    name = "Location - User Activity Recognition",
    description = "This Sample demonstrate detection of user activity like walking, driving, etc.",
    documentation = "https://developer.android.com/training/location/transitions",
)*/
@Composable
fun UserActivityRecognitionScreen() {
    val activityPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        Manifest.permission.ACTIVITY_RECOGNITION
    } else {
        "com.google.android.gms.permission.ACTIVITY_RECOGNITION"
    }

    PermissionBox(permissions = listOf(activityPermission)) {
        UserActivityRecognitionContent()
    }
}

@SuppressLint("InlinedApi")
@RequiresPermission(
    anyOf = [
        Manifest.permission.ACTIVITY_RECOGNITION,
        "com.google.android.gms.permission.ACTIVITY_RECOGNITION",
    ],
)
@Composable
fun UserActivityRecognitionContent() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val manager = remember {
        UserActivityTransitionManager(context)
    }
    var currentUserActivity by remember {
        mutableStateOf("Unknown")
    }

    // Calling deregister on dispose
    DisposableEffect(LocalLifecycleOwner.current) {
        onDispose {
            scope.launch(Dispatchers.IO) {
                manager.deregisterActivityTransitions()
            }
        }
    }

    // Register a local broadcast to receive activity transition updates
    UserActivityBroadcastReceiver(systemAction = CUSTOM_INTENT_USER_ACTION) { userActivity ->
        currentUserActivity = userActivity
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Button(
            onClick = {
                scope.launch(Dispatchers.IO) {
                    manager.registerActivityTransitions()
                }

            },
        ) {
            Text(text = "Register for activity transition updates")
        }
        Button(
            onClick = {
                currentUserActivity = ""
                scope.launch(Dispatchers.IO) {
                    manager.deregisterActivityTransitions()
                }
            },
        ) {
            Text(text = "Deregister for activity transition updates")
        }
        if (currentUserActivity.isNotBlank()) {
            Text(
                text = "CurrentActivity is = $currentUserActivity",
            )
        }
    }
}

