package com.example.wearos_ingestion.presentation.presentation.geobubble


import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.wearos_ingestion.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.TileOverlayOptions
import com.google.maps.android.SphericalUtil
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.TileOverlay
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.heatmaps.HeatmapTileProvider
import com.google.maps.android.heatmaps.WeightedLatLng
import java.lang.Float.parseFloat

@Composable
fun GeobubbleMap(
    navController: NavHostController,
    lat: Double,
    lon: Double,
    modifier: Modifier = Modifier
) {
    // State variable to track if the first pair of lat and lon has been received
    var isFirstPairReceived by remember { mutableStateOf(false) }
    // Variable to store the first pair of lat and lon
    var firstLat by remember { mutableStateOf(lat) }
    var firstLon by remember { mutableStateOf(lon) }

    // Variable to store the latest valid coordinates
    var latestLat by remember { mutableDoubleStateOf(lat) }
    var latestLon by remember { mutableDoubleStateOf(lon) }
    //var latestLatLon by remember { mutableListOf(LatLng(lat,lon)) }

    // Variable to store the furthest distance from the first coordinate
    var furthestDistance by remember { mutableStateOf(0.0) }

    // Mutable collection to store WeightedLatLng points
    val weightedLatLngPoints = remember { mutableStateListOf<WeightedLatLng>() }

    val latLngPoints = remember { mutableStateListOf<LatLng?>() }

    // Check if latitude and longitude are not null and update the latest coordinates
    if (lat != null && lon != null) {
        // Update the latest coordinates
        latestLat = lat
        latestLon = lon

        // Add the latest coordinates to the list with a default weight
        weightedLatLngPoints.add(WeightedLatLng(LatLng(lat, lon), 1.0))

        // Add the latest coordinates to the list
        latLngPoints.add(LatLng(lat, lon))

        // If it's the first pair, update the firstLat and firstLon
        if (!isFirstPairReceived) {
            isFirstPairReceived = true
            firstLat = lat
            firstLon = lon
            Log.d("Initial Coordinates", "Lat: $firstLat, Lon: $firstLon")
        }else{
            // Calculate the distance between the current coordinate and the first coordinate
            val distance = SphericalUtil.computeDistanceBetween(
                LatLng(firstLat, firstLon),
                LatLng(lat, lon)
            )
            // Update the furthest distance if the current distance is greater
            if (distance > furthestDistance) {
                furthestDistance = distance
            }
        }
    }



    var uiSettings by remember { mutableStateOf(MapUiSettings()) }
    var properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.SATELLITE))
    }
    val userLocation = LatLng(lat, lon)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userLocation, 10f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = properties,
        uiSettings = uiSettings
    ) {
        Marker(
            state = MarkerState(position = userLocation),
            title = "User Location",
            snippet = "$lat, $lon",
            icon = BitmapDescriptorFactory.fromResource(R.drawable.grandma_emoji) //Hi Grandma
            //icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
        )
        Marker(
            state = MarkerState(position = LatLng(firstLat, firstLon)),
            title = "Start Location",
            snippet = "$firstLat, $firstLon"
        )
        /*Circle(
            center = LatLng(firstLat, firstLon),
            radius = furthestDistance,
            fillColor = Color.Blue
        )*/
        // Adding Heatmap
        val heatmapPoints = remember {
            mutableStateListOf(
                LatLng(latestLat, latestLon)
            )
        }

        /*val heatmapTileProvider = remember {
            HeatmapTileProvider.Builder()
                .data(heatmapPoints)
                .build()
        }*/
        // Adding Heatmap with LatLng points
        val weightedLatLngPoints = latLngPoints.mapNotNull { latLng ->
            latLng?.let { WeightedLatLng(it, 1.0) }
        }
        val heatmapTileProvider =  HeatmapTileProvider.Builder().weightedData(weightedLatLngPoints).build()



        TileOverlay(
            tileProvider = heatmapTileProvider,

        )
    }

}