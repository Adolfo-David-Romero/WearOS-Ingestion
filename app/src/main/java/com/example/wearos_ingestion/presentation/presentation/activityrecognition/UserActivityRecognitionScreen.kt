package com.example.wearos_ingestion.presentation.presentation.activityrecognition

import WalkingARToggle
import android.annotation.SuppressLint
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Arrangement
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
import com.example.wearos_ingestion.presentation.app.CUSTOM_INTENT_USER_ACTION
import com.example.wearos_ingestion.presentation.service.UserActivityBroadcastReceiver
import com.example.wearos_ingestion.presentation.service.UserActivityTransitionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import android.Manifest
import android.os.Build
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import com.example.wearos_ingestion.presentation.presentation.measure.BackNavigationButton
import com.example.wearos_ingestion.presentation.theme.IngestionAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.catalog.framework.annotations.Sample


//const val CUSTOM_INTENT_USER_ACTION = "USER-ACTIVITY-DETECTION-INTENT-ACTION"

@SuppressLint("MissingPermission")
@Sample(
    name = "Location - User Activity Recognition",
    description = "This Sample demonstrate detection of user activity like walking, driving, etc.",
    documentation = "https://developer.android.com/training/location/transitions",
)
@Composable
fun UserActivityRecognitionScreen(
    navController: NavHostController
) {
    val activityPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        Manifest.permission.ACTIVITY_RECOGNITION
    } else {
        "com.google.android.gms.permission.ACTIVITY_RECOGNITION"
    }

    PermissionBox(permissions = listOf(activityPermission)) {
        UserActivityRecognitionContent(navController)
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
fun UserActivityRecognitionContent(
    navController: NavHostController
) {
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
                Log.d("UserActivityRecognition", "Activity transitions deregistered")
            }
        }
    }

    // Register a local broadcast to receive activity transition updates
    UserActivityBroadcastReceiver(systemAction = CUSTOM_INTENT_USER_ACTION) { userActivity ->
        currentUserActivity = userActivity
        Log.d("UserActivityRecognition", "Received user activity: $userActivity")
    }

    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            BackNavigationButton(navController = navController)
        }
        item {

        }

        item {
            Button(
                onClick = {
                    scope.launch(Dispatchers.IO) {
                        manager.registerActivityTransitions()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            )
            {
                Text(text = "Register for activity transition updates")
            }
        }
        item {
            Button(
                onClick = {
                    currentUserActivity = ""
                    scope.launch(Dispatchers.IO) {
                        manager.deregisterActivityTransitions()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            ) {
                Text(text = "Deregister for activity transition updates")
            }
        }
        item {
            if (currentUserActivity.isNotBlank()) {
                Text(
                    text = "CurrentActivity is = $currentUserActivity",

                )
            }
        }

    }
}

/*
@ExperimentalPermissionsApi
@Preview(
    device = Devices.WEAR_OS_SMALL_ROUND,
    showBackground = false,
    showSystemUi = true
)
@Composable
fun UserActivityRecognitionScreenPreview() {
    val context = LocalContext.current // Mock implementation of LocalContext
    val lifecycleOwner = LocalLifecycleOwner.current // Mock implementation of LocalLifecycleOwner

    // Provide mock values for CompositionLocals
    CompositionLocalProvider(
        LocalContext provides context,
        LocalLifecycleOwner provides lifecycleOwner
    ) {
        IngestionAppTheme {
            val navController = rememberNavController() // Create a NavController instance
            UserActivityRecognitionContent(navController = navController)

        }
    }
}
*/
