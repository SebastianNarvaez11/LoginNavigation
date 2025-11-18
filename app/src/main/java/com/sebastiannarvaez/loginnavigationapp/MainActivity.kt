package com.sebastiannarvaez.loginnavigationapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sebastiannarvaez.loginnavigationapp.core.presentation.navigation.NavigationWrapper
import com.sebastiannarvaez.loginnavigationapp.core.presentation.theme.LoginNavigationAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginNavigationAppTheme {
                NavigationWrapper()
            }
        }
    }
}
