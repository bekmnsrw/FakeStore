package com.bekmnsrw.fakestore.core.permission

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.compose.material.AlertDialog
import androidx.compose.material.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.bekmnsrw.fakestore.R
import com.bekmnsrw.fakestore.core.database.DataStoreManager
import com.bekmnsrw.fakestore.ui.theme.CustomTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun RequestNotificationPermissionDialog() {
    val permissionState = rememberPermissionState(
        permission = Manifest.permission.POST_NOTIFICATIONS
    )

    val dataStoreManager = DataStoreManager(LocalContext.current)

    val shouldShow = dataStoreManager.getShouldShow.collectAsState(initial = false).value

    val isKeyStored = dataStoreManager.isKeyStored(
        booleanPreferencesKey(DataStoreManager.SHOULD_SHOW_RATIONALE_KEY)
    ).collectAsState(initial = false).value

    if (!permissionState.status.isGranted) {
        if (permissionState.status.shouldShowRationale) {
            // Ask user again to grant permissions
            when {
                !isKeyStored -> RationaleDialog(shouldShow = true)
                shouldShow != null -> RationaleDialog(shouldShow = shouldShow)
            }
        } else {
            // Request permissions for the first time
            LaunchedEffect(Unit) {
                permissionState.launchPermissionRequest()
            }
        }
    }
}

@Composable
fun RationaleDialog(shouldShow: Boolean) {
    val context = LocalContext.current
    val dataStoreManager = DataStoreManager(context)

    if (shouldShow) {
        AlertDialog(
            title = {
                Text(
                    text = stringResource(
                        id = R.string.rationale_dialog_title
                    ),
                    style = CustomTheme.typography.rationaleDialogTitle,
                    color = CustomTheme.colors.onBackground
                )
            },
            text = {
                Text(
                    text = stringResource(
                        id = R.string.rationale_dialog_text
                    ),
                    style = CustomTheme.typography.rationaleDialogText,
                    color = CustomTheme.colors.onBackground
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Intent to App Settings
                        context.startActivity(
                            Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", context.packageName, null)
                            ).apply {
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                                addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                            }
                        )
                    }
                ) {

                    Text(
                        text = stringResource(
                            id = R.string.rationale_dialog_confirm_button_text
                        ),
                        style = CustomTheme.typography.rationaleDialogConfirmButton,
                        color = CustomTheme.colors.onBackground
                    )
                }
            },
            dismissButton = {
                TextButton(
                    // Remember that user don't want to see this dialog again
                    onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            dataStoreManager.saveShouldShow(
                                shouldShow = false
                            )
                        }
                    }
                ) {

                    Text(
                        text = stringResource(
                            id = R.string.rationale_dialog_dismiss_button_text
                        ),
                        style = CustomTheme.typography.rationaleDialogDismissButton,
                        color = CustomTheme.colors.onBackground
                    )
                }
            },
            onDismissRequest = {
                CoroutineScope(Dispatchers.IO).launch {
                    dataStoreManager.saveShouldShow(
                        shouldShow = true
                    )
                }
            }
        )
    }
}
