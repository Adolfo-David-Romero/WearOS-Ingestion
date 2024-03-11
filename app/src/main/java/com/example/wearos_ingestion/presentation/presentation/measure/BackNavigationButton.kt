package com.example.wearos_ingestion.presentation.presentation.measure

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.Button
import com.example.wearos_ingestion.presentation.theme.IngestionAppTheme

@Composable
fun BackNavigationButton(navController: NavHostController){
    Button(
        onClick = {
            navController.popBackStack()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text("Go back")
    }
}

@Preview(
    device = Devices.WEAR_OS_SMALL_ROUND,
    showSystemUi = true
)
@Composable
fun BackNavigationPreview() {
    IngestionAppTheme {
        BackNavigationButton(navController = rememberNavController())
    }
}
