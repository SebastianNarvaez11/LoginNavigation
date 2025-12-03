package com.sebastiannarvaez.loginnavigationapp.core.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import com.sebastiannarvaez.loginnavigationapp.core.presentation.components.BottomNavigationBar
import com.sebastiannarvaez.loginnavigationapp.feature.auth.presentation.AuthStatus
import com.sebastiannarvaez.loginnavigationapp.feature.auth.presentation.AuthViewModel
import com.sebastiannarvaez.loginnavigationapp.feature.auth.presentation.navigation.authGraph

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
            AnimatedVisibility(
                visible = appState.shouldShowBottomBar && authStatus == AuthStatus.Authenticated,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
            ) {
                BottomNavigationBar(navController, navController.currentDestination)
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Destinations.Expenses,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            authGraph(
                navController = navController,
                onShowSnackbar = { snackbarHostState.showSnackbar(it) }
            )

            mainGraph(
                navController = navController,
                onShowSnackbar = { snackbarHostState.showSnackbar(it) },
                authVM = authVM
            )
        }
    }
}