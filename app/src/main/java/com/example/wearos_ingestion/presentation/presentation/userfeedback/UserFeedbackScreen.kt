package com.example.wearos_ingestion.presentation.presentation.userfeedback

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import com.example.wearos_ingestion.presentation.presentation.home.BackNavigationButton

@Composable
fun UserFeedbackScreen(navController: NavHostController) {
    var feedbackRatingEoU by remember { mutableFloatStateOf(5f) } //Ease of use
    var feedbackRatingReadability by remember { mutableFloatStateOf(5f) } //Readability
    var feedbackRatingPerformance by remember { mutableFloatStateOf(5f) } //performance
    var feedbackRatingNotifications by remember { mutableFloatStateOf(5f) } //Notification
    var feedbackRatingOverall by remember { mutableFloatStateOf(5f) } //Overall

    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { BackNavigationButton(navController = navController) }
        //--
        item {
            Text(
                text = "Do you feel this App is easy to handle?",
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
        item {
            Text(
                text = feedbackRatingEoU.toInt().toString(),
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        item {
            Slider(
                value = feedbackRatingEoU,
                onValueChange = { feedbackRatingEoU = it },
                valueRange = 1f..10f,
                steps = 8,
                colors = SliderDefaults.colors(
                    activeTrackColor = MaterialTheme.colorScheme.primary,
                    inactiveTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                    thumbColor = MaterialTheme.colorScheme.primary
                )
            )
        }
        //--
        item {
            Text(
                text = "Do you feel you can read the app easily?",
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
        item {
            Text(
                text = feedbackRatingReadability.toInt().toString(),
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        item {
            Slider(
                value = feedbackRatingReadability,
                onValueChange = { feedbackRatingReadability = it },
                valueRange = 1f..10f,
                steps = 8,
                colors = SliderDefaults.colors(
                    activeTrackColor = MaterialTheme.colorScheme.primary,
                    inactiveTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                    thumbColor = MaterialTheme.colorScheme.primary
                )
            )
        }
        //--
        item {
            Text(
                text = "Is the app fast and responsive?",
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
        item {
            Text(
                text = feedbackRatingPerformance.toInt().toString(),
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        item {
            Slider(
                value = feedbackRatingPerformance,
                onValueChange = { feedbackRatingPerformance = it },
                valueRange = 1f..10f,
                steps = 8,
                colors = SliderDefaults.colors(
                    activeTrackColor = MaterialTheme.colorScheme.primary,
                    inactiveTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                    thumbColor = MaterialTheme.colorScheme.primary
                )
            )
        }
        //--
        item {
            Text(
                text = "Are the Notifications Helpful?",
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
        item {
            Text(
                text = feedbackRatingNotifications.toInt().toString(),
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        item {
            Slider(
                value = feedbackRatingNotifications,
                onValueChange = { feedbackRatingNotifications = it },
                valueRange = 1f..10f,
                steps = 8,
                colors = SliderDefaults.colors(
                    activeTrackColor = MaterialTheme.colorScheme.primary,
                    inactiveTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                    thumbColor = MaterialTheme.colorScheme.primary
                )
            )
        }
        //--
        item {
            Text(
                text = "How do you feel about App overall?",
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
        item {
            Text(
                text = feedbackRatingOverall.toInt().toString(),
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        item {
            Slider(
                value = feedbackRatingOverall,
                onValueChange = { feedbackRatingOverall = it },
                valueRange = 1f..10f,
                steps = 8,
                colors = SliderDefaults.colors(
                    activeTrackColor = MaterialTheme.colorScheme.primary,
                    inactiveTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                    thumbColor = MaterialTheme.colorScheme.primary
                )
            )
        }

        item {
            Button(
                onClick = { /* Handle submit action */ },
                modifier = Modifier.padding(
                    16.dp
                )
            ) {
                Text("Submit")
            }
        }
    }

}