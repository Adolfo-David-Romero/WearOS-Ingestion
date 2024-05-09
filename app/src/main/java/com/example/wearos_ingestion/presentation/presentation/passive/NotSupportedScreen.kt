package com.example.wearos_ingestion.presentation.presentation.passive

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.HeartBroken
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.example.wearos_ingestion.R
import com.example.wearos_ingestion.presentation.presentation.home.BackNavigationButton
import com.example.wearos_ingestion.presentation.theme.IngestionAppTheme

@Composable
fun NotSupportedScreen(
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BackNavigationButton(navController = navController)
        Icon(
            imageVector = Icons.Default.HeartBroken,
            contentDescription = stringResource(id = R.string.broken_heart_description),
            tint = Color.Red
        )
        Text(
            text = stringResource(id = R.string.not_available),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(
    device = Devices.WEAR_OS_SMALL_ROUND,
    showSystemUi = true
)
@Composable
fun NotSupportedScreenPreview() {
    val navController = rememberNavController() // Create a NavController instance
    IngestionAppTheme {
        NotSupportedScreen(navController)
    }
}