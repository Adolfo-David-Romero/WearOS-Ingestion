package com.example.wearos_ingestion.presentation.presentation.geobubble


import android.graphics.Color
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.wearos_ingestion.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.TileOverlayOptions
import com.google.maps.android.SphericalUtil
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.TileOverlay
import com.google.maps.android.compose.TileOverlayState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberTileOverlayState
import com.google.maps.android.heatmaps.Gradient
import com.google.maps.android.heatmaps.HeatmapTileProvider
import com.google.maps.android.heatmaps.WeightedLatLng
import com.google.maps.android.ktx.model.tileOverlayOptions

@GoogleMapComposable
@Composable
fun GeobubbleMap(
    lat: Double,
    lon: Double,
    modifier: Modifier = Modifier
) {
    // Variables to store the first pair of lat and lon
    var firstLat by remember { mutableStateOf(lat) }
    var firstLon by remember { mutableStateOf(lon) }
    var isFirstPairReceived by remember { mutableStateOf(false) } // State variable to track if the first pair of lat and lon has been received

    // Variable to store the latest valid coordinates
    var latestLat by remember { mutableDoubleStateOf(lat) }
    var latestLon by remember { mutableDoubleStateOf(lon) }
    val latestLatLon = remember { mutableStateListOf<LatLng?>() }
    val weighted =
        remember { mutableStateListOf<WeightedLatLng?>(WeightedLatLng(LatLng(lat, lon))) }
    val weightedArrayList = arrayListOf<WeightedLatLng>()
    var weightedLatLngs: List<WeightedLatLng> = weightedArrayList
    val arrayList = arrayListOf<LatLng>()
    //var arrayList = remember { mutableStateListOf<LatLng>() }
    //val latLngsPoints: List<LatLng> = arrayList
    // State to store the list of LatLng points
    val latLngsPoints = remember { mutableStateListOf<LatLng>() }

    // Add the latest coordinates to the list
    LaunchedEffect(lat, lon) {
        latLngsPoints.add(LatLng(lat, lon))
    }

    // Variable to store the furthest distance from the first coordinate
    var furthestDistance by remember { mutableStateOf(0.0) }

    // Check if latitude and longitude are not null and update the latest coordinates
    if (lat != null && lon != null) {
        // Update the latest coordinates
        latestLat = lat
        latestLon = lon
/*        // Add the latest coordinates to the list
        latestLatLon.add(LatLng(lat, lon))
        weightedArrayList.add(WeightedLatLng(LatLng(lat, lon)))
        arrayList.add(LatLng(lat, lon))*/

        // If it's the first pair, update the firstLat and firstLon
        if (!isFirstPairReceived) {
            isFirstPairReceived = true
            firstLat = lat
            firstLon = lon
            Log.d("Initial Coordinates", "Lat: $firstLat, Lon: $firstLon")
        } else {
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
        AddHeatMap(latLngsPoints.toList())
    }
}

@GoogleMapComposable
@Composable
fun AddHeatMap(latLngsPoints: List<LatLng>) {

    // Create the gradient.
    val colors = intArrayOf(
        Color.rgb(102, 225, 0),  // green
        Color.rgb(255, 0, 0) // red
    )
    val startPoints = floatArrayOf(0.2f, 1f)
    val gradient = Gradient(colors, startPoints)

    val tileOverlayState = rememberTileOverlayState()
    val heatMapProvider = remember {
        HeatmapTileProvider.Builder()
            .data(latLngsPoints)
            .gradient(gradient)
            .build()
    }
    // Ensure that the heatmap data is updated only when the latLngsPoints change.
    LaunchedEffect(latLngsPoints) {
        heatMapProvider.setData(latLngsPoints)
    }


    // Configure additional properties of the heatmap provider if needed.
    LaunchedEffect(Unit) {
        heatMapProvider.setOpacity(0.7)
    }
    //heatMapProvider.setData(latLngsPoints)

    TileOverlay(
        tileProvider = heatMapProvider,
        state = tileOverlayState
    )



}