package com.bekmnsrw.fakestore

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.AlertDialog
import androidx.compose.material.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun RequestNotificationPermissionDialog() {
    val permissionState = rememberPermissionState(
        permission = Manifest.permission.POST_NOTIFICATIONS
    )

    if (!permissionState.status.isGranted) {
        if (permissionState.status.shouldShowRationale) {
            RationaleDialog()
        } else {
            LaunchedEffect(!permissionState.status.shouldShowRationale) {
                permissionState.launchPermissionRequest()
            }
        }
    }
}

@Composable
fun PermissionDialog(
    onRequestPermission: () -> Unit
) {

    var showWarningDialog by remember { mutableStateOf(true) }

    if (showWarningDialog) {
        AlertDialog(
            title = {
                Text(text = "Title")
            },
            text = {
                Text(
                    text = "Text"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onRequestPermission()
                        showWarningDialog = false
                    }
                ) {

                    Text(
                        text = "Request"
                    )
                }
            },
            onDismissRequest = {}
        )
    }
}

@Composable
fun RationaleDialog() {

    var showWarningDialog by remember { mutableStateOf(true) }

    if (showWarningDialog) {
        AlertDialog(
            title = {
                Text(
                    text = ""
                )
            },
            text = {
                Text(
                    text = ""
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showWarningDialog = false
                    }
                ) {

                    Text(
                        text = ""
                    )
                }
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }
}
