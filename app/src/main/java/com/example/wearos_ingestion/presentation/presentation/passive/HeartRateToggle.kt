package com.example.wearos_ingestion.presentation.presentation.passive

import androidx.compose.runtime.Composable
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
fun HeartRateToggle(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    permissionState: PermissionState,
    modifier: Modifier = Modifier
) {
    ToggleChip(
        modifier = modifier,
        checked = checked,
        colors = ToggleChipDefaults.toggleChipColors(),
        onCheckedChange = { enabled ->
            if (permissionState.status.isGranted) {
                onCheckedChange(enabled)
            } else {
                permissionState.launchPermissionRequest()
            }
        },
        label = { Text(stringResource(id = R.string.heart_rate_toggle)) },
        toggleControl = {
            Icon(
                imageVector = ToggleChipDefaults.switchIcon(checked),
                contentDescription = stringResource(id = R.string.heart_rate_toggle)
            )
        }
    )
}
@OptIn(ExperimentalPermissionsApi::class)
@Preview(
    device = Devices.WEAR_OS_SMALL_ROUND,
    showSystemUi = true
)
@Composable
fun HeartRateTogglePreview() {
    val permissionState = object : PermissionState {
        override val permission = PERMISSION
        override val status: PermissionStatus = PermissionStatus.Granted
        override fun launchPermissionRequest() {}
    }
    IngestionAppTheme {
        HeartRateToggle(
            checked = true,
            onCheckedChange = {},
            permissionState = permissionState
        )
    }
}