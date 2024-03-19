package com.example.wearos_ingestion.presentation.presentation.passive

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ActivityRecognitionNavigationButton(navController: NavController) {
    Button(
        onClick = {
            navController.navigate("userActivityRecognitionScreen")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text("Go to User activity screen")
    }

}