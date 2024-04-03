import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.ToggleChip
import androidx.wear.compose.material.ToggleChipDefaults
import com.example.wearos_ingestion.R
import com.example.wearos_ingestion.presentation.app.PERMISSION
import com.example.wearos_ingestion.presentation.theme.IngestionAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WalkingARToggle(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,

) {

    ToggleChip(
        modifier = modifier,
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = ToggleChipDefaults.toggleChipColors(),
        label = { Text(text) },
        toggleControl = {
            Icon(
                imageVector = ToggleChipDefaults.switchIcon(checked),
                contentDescription = stringResource(id = R.string.walking_ar_toggle)
            )
        }
    )
}
/*
@OptIn(ExperimentalPermissionsApi::class)
@Preview(
    device = Devices.WEAR_OS_SMALL_ROUND,
    showSystemUi = true
)
@Composable
fun WalkingARTogglePreview() {
    IngestionAppTheme {
        var isChecked by remember { mutableStateOf(true) } // Example toggle state
        WalkingARToggle(
            text = "WALKING",
            modifier = Modifier,

        )
    }
}*/
