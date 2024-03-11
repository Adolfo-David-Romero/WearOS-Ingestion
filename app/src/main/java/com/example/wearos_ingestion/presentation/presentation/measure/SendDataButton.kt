package com.example.wearos_ingestion.presentation.presentation.measure

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import com.example.wearos_ingestion.presentation.service.FirebaseHandler
import com.example.wearos_ingestion.presentation.theme.IngestionAppTheme

@Composable
fun SendDataButton(sensorData: String, onClick: () -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Button(
        onClick = {
            FirebaseHandler().sendDataToFirebase(sensorData)
            onClick()
            keyboardController?.hide()
        },
        modifier = Modifier
            .padding(top = 16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Send,
            contentDescription = "Send Data"
        )
        Spacer(modifier = Modifier.width(8.dp))
        //Text("Send Data")
    }
}
@Preview(
    device = Devices.WEAR_OS_SMALL_ROUND,
    showSystemUi = true
)
@Composable
fun SendDataButtonPreview() {
    IngestionAppTheme {
        SendDataButton(sensorData = "") {
            
        }
    }
}