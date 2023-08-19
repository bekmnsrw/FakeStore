package com.bekmnsrw.fakestore.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import com.bekmnsrw.fakestore.core.navigation.NavigationHost
import com.bekmnsrw.fakestore.ui.theme.Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Theme {
                CompositionLocalProvider {
                    NavigationHost()
                }
            }
        }
    }
}
