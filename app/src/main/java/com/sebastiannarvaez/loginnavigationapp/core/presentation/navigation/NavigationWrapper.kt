package com.sebastiannarvaez.loginnavigationapp.core.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import com.sebastiannarvaez.loginnavigationapp.core.presentation.components.BottomNavigationBar
import com.sebastiannarvaez.loginnavigationapp.feature.auth.presentation.AuthStatus
import com.sebastiannarvaez.loginnavigationapp.feature.auth.presentation.AuthViewModel
import com.sebastiannarvaez.loginnavigationapp.feature.auth.presentation.navigation.authGraph
import kotlinx.coroutines.launch

@Composable
fun NavigationWrapper(
    appState: MyAppState = rememberMyAppState(),
    authVM: AuthViewModel = hiltViewModel()
) {
    val navController = appState.navController
    val authStatus by authVM.authStatusStream.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        bottomBar = {
            if (appState.shouldShowBottomBar && authStatus == AuthStatus.Authenticated) {
                BottomNavigationBar(navController, navController.currentDestination)
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Destinations.Home,
            modifier = Modifier.padding(innerPadding)
        ) {
            authGraph(
                navController = navController,
                onShowSnackbar = { snackbarHostState.showSnackbar(it) }
            )

            mainGraph(navController, authVM)
        }
    }
}