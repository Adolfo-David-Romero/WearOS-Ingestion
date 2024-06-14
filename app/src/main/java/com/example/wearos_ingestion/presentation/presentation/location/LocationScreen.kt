package com.example.wearos_ingestion.presentation.presentation.location

/*
 * Copyright 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.Manifest
import android.annotation.SuppressLint
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import com.example.wearos_ingestion.presentation.presentation.activityrecognition.PermissionBox
import com.example.wearos_ingestion.presentation.presentation.home.BackNavigationButton
import com.example.wearos_ingestion.presentation.presentation.measure.TAG
import com.example.wearos_ingestion.presentation.service.FirebaseHandler
import com.google.android.catalog.framework.annotations.Sample
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import androidx.work.*

@SuppressLint("MissingPermission")
@Sample(
    name = "Location - Getting Current Location",
    description = "This Sample demonstrate how to request of current location",
    documentation = "https://developer.android.com/training/location/retrieve-current",
)
@Composable
fun CurrentLocationScreen(navController: NavHostController) {
    val permissions = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )
    PermissionBox(
        permissions = permissions,
        requiredPermissions = listOf(permissions.first()),
        onGranted = {
            CurrentLocationContent(
                usePreciseLocation = it.contains(Manifest.permission.ACCESS_FINE_LOCATION),
                navController = navController
            )
        },
    )
}


@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION],
)
@Composable
fun CurrentLocationContent(
    usePreciseLocation: Boolean,
    navController: NavHostController
) {
    var isButtonClicked by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val locationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }
    val userLocation by remember {
        mutableStateOf("")
    }

    // The location request that defines the location updates
    var locationRequest by remember {
        mutableStateOf<LocationRequest?>(null)
    }
    // Keeps track of received location updates as text
    var locationUpdates by remember {
        mutableStateOf("")
    }
    // Keeps track of the last known location
    var lastKnownLocation by remember {
        mutableStateOf<Pair<Double, Double>?>(null)
    }
    val firebaseHandler = FirebaseHandler()

    // Only register the location updates effect when we have a request
    if (locationRequest != null) {
        LocationUpdatesEffect(locationRequest!!, isButtonClicked = isButtonClicked) { result ->
            // For each result update the text
            for (currentLocation in result.locations) {
                /*                locationUpdates = "${System.currentTimeMillis()}:\n" +
                                        "- @lat: ${currentLocation.latitude}\n" +
                                        "- @lng: ${currentLocation.longitude}\n" +
                                        "- Accuracy: ${currentLocation.accuracy}\n\n" +
                                        locationUpdates*/

                locationUpdates = "Current Location is \n" + "lat : ${currentLocation.latitude}\n" +
                        "long : ${currentLocation.longitude}\n" + "fetched at ${System.currentTimeMillis()}\n" +
                        "Accuracy: ${currentLocation.accuracy}\n\n"//+locationUpdates

                lastKnownLocation = Pair(currentLocation.latitude, currentLocation.longitude)
            }
        }
    }

    ScalingLazyColumn(
        Modifier
            .fillMaxWidth()
            .animateContentSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            BackNavigationButton(navController = navController)
        }
        item {
            Text(
                text = locationUpdates,
                color = Color.White
            )
        }
        item {
            Button(
                onClick = {
                    // getting last known location is faster and minimizes battery usage
                    // This information may be out of date.
                    // Location may be null as previously no client has access location
                    // or location turned of in device setting.
                    // Please handle for null case as well as additional check can be added before using the method
                    scope.launch(Dispatchers.IO) {
                        val result = locationClient.lastLocation.await()
                        locationUpdates = if (result == null) {
                            "No last known location. Try fetching the current location first"
                        } else {
                            lastKnownLocation = Pair(result.latitude, result.longitude)
                            "Last known location is \n" + "lat : ${result.latitude}\n" +
                                    "long : ${result.longitude}\n" + "fetched at ${System.currentTimeMillis()}\n" +
                                    "Accuracy: ${result.accuracy}"

                        }
                    }
                },
            ) {
                Text("Get last known location")
            }
        }
        item {
            Button(
                onClick = {
                    //To get more accurate or fresher device location use this method
                    scope.launch(Dispatchers.IO) {
                        val priority = if (usePreciseLocation) {
                            Priority.PRIORITY_HIGH_ACCURACY
                        } else {
                            Priority.PRIORITY_BALANCED_POWER_ACCURACY
                        }
                        val result = locationClient.getCurrentLocation(
                            priority,
                            CancellationTokenSource().token,
                        ).await()
                        result?.let { fetchedLocation ->
                            locationUpdates =
                                "Location update is \n" + "lat : ${fetchedLocation.latitude}\n" +
                                        "long : ${fetchedLocation.longitude}\n" + "fetched at ${System.currentTimeMillis()}"

                            Log.d(TAG, "Location Info: $locationUpdates")

                            lastKnownLocation = Pair(fetchedLocation.latitude, fetchedLocation.longitude)
                        }
                    }
                },
            ) {
                Text(text = "Get current location")
            }
        }
        item {

            Button(onClick = {
                // Toggle the button state
                isButtonClicked = !isButtonClicked
                scope.launch(Dispatchers.IO) {
                    //--
                    locationRequest = if (isButtonClicked) {
                        // Define the accuracy based on your needs and granted permissions
                        val priority = if (usePreciseLocation) {
                            Priority.PRIORITY_HIGH_ACCURACY
                        } else {
                            Priority.PRIORITY_BALANCED_POWER_ACCURACY
                        }
                        LocationRequest.Builder(priority, TimeUnit.SECONDS.toMillis(3)).build()
                    } else {
                        locationUpdates = "Stopped Location Update"
                        null
                    }
                }
            }) {
                Text(text = if (isButtonClicked) "Stop Location Updates" else "Request Location Updates")
            }
        }
/*        item {
            Button(onClick = {
                lastKnownLocation?.let { (latitude, longitude) ->
                    val db = FirebaseFirestore.getInstance()
                    val locationData = hashMapOf(
                        "latitude" to latitude,
                        "longitude" to longitude,
                        "timestamp" to System.currentTimeMillis()
                    )

                    scope.launch(Dispatchers.IO) {
                        try {
                            db.collection("locations")
                                .add(locationData)
                                .await()
                            Log.d(TAG, "Location sent to Firebase successfully")
                        } catch (e: Exception) {
                            Log.e(TAG, "Error sending location to Firebase", e)
                        }
                    }
                } ?: run {
                    Log.w(TAG, "No location data available to send to Firebase")
                }
            }) {
                Text(text = "Send Location to Firebase")
            }
        }*/
        item {
            Button(onClick = {
                lastKnownLocation?.let { (latitude, longitude) ->
                    firebaseHandler.sendLocationToFirebase(latitude, longitude)
                } ?: run {
                    Log.w(TAG, "No location data available to send to Firebase")
                }
            }) {
                Text(text = "Send Location to Firebase")
            }
        }

    }
}

@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION],
)
@Composable
fun LocationUpdatesEffect(
    locationRequest: LocationRequest,
    isButtonClicked: Boolean,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onUpdate: (result: LocationResult) -> Unit,
) {
    val context = LocalContext.current
    val currentOnUpdate by rememberUpdatedState(newValue = onUpdate)

    DisposableEffect(locationRequest, isButtonClicked, lifecycleOwner) {
        val locationClient = LocationServices.getFusedLocationProviderClient(context)
        val locationCallback: LocationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                currentOnUpdate(result)
            }
        }
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START && isButtonClicked && locationRequest != null) {
                locationClient.requestLocationUpdates(
                    locationRequest, locationCallback, Looper.getMainLooper(),
                )

            } else if ((event == Lifecycle.Event.ON_STOP || !isButtonClicked) && locationRequest != null) {
                locationClient.removeLocationUpdates(locationCallback)

            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            locationClient.removeLocationUpdates(locationCallback)
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

