package com.sebastiannarvaez.loginnavigationapp.feature.auth.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sebastiannarvaez.loginnavigationapp.core.presentation.navigation.Destinations
import com.sebastiannarvaez.loginnavigationapp.feature.auth.presentation.login.LoginScreen
import com.sebastiannarvaez.loginnavigationapp.feature.auth.presentation.register.RegisterScreen

fun NavGraphBuilder.authGraph(
    navController: NavController,
    onShowSnackbar: suspend (message: String) -> Unit
) {
    composable<Destinations.Login> {
        LoginScreen(
            navigateToHome = {
                navController.navigate(Destinations.Home) {
                    popUpTo(Destinations.Login) { inclusive = true }
                }
            },
            onShowSnackbar = { onShowSnackbar(it) }
        )
    }

    composable<Destinations.Register> {
        RegisterScreen(navigateBack = {
            navController.popBackStack()
        })
    }
}