import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material.Text
import com.example.wearos_ingestion.presentation.presentation.adl.NotificationReceiver
import com.example.wearos_ingestion.presentation.theme.IngestionAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import java.time.LocalTime



@Composable
fun ADLScreen() {
    IngestionAppTheme {
        var selectedTime by remember { mutableStateOf(Calendar.getInstance()) }
        var timeText by remember { mutableStateOf("") }

        val keyboardController = LocalSoftwareKeyboardController.current

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ScalingLazyColumn(
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                item {
                    OutlinedTextField(
                        value = timeText,
                        onValueChange = {
                            timeText = it
                            if (it.length == 2 && it.contains(":")) {
                                val hour = it.substring(0, 2).toIntOrNull() ?: 0
                                val minute = it.substring(3, it.length).toIntOrNull() ?: 0
                                selectedTime.set(Calendar.HOUR_OF_DAY, hour)
                                selectedTime.set(Calendar.MINUTE, minute)
                            }
                        },
                        label = { Text(text = "Time (hh:mm)") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                            }
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    Button(onClick = {
                        // Save the selected time
                        // For demonstration, you can replace it with your logic to save the time
                        // For example, you can use SharedPreferences or any other data storage mechanism
                        // For simplicity, just printing the selected time here
                        println(
                            "Selected Time: ${selectedTime.get(Calendar.HOUR_OF_DAY)}:${
                                selectedTime.get(
                                    Calendar.MINUTE
                                )
                            }"
                        )
                    }) {
                        Text(text = "Save", fontSize = 18.sp)
                    }
                }
            }
        }
    }
}





@ExperimentalPermissionsApi
@Preview(
    device = Devices.WEAR_OS_SMALL_ROUND,
    showBackground = false,
    showSystemUi = true
)
@Preview
@Composable
fun PreviewMyApp() {
    ADLScreen()
}
