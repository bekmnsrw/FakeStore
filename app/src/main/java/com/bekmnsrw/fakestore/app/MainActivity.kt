package com.bekmnsrw.fakestore.app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import com.bekmnsrw.fakestore.core.navigation.NavHost
import com.bekmnsrw.fakestore.ui.theme.Theme
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            // TODO: Save FCM registration token to backend
            Log.d("FCM registration token", Firebase.messaging.token.await())
        }

        setContent {
            Theme {
                CompositionLocalProvider {
                    NavHost()
                }
            }
        }
    }
}
