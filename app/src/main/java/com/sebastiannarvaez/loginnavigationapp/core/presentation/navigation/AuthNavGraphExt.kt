package com.sebastiannarvaez.loginnavigationapp.core.presentation.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sebastiannarvaez.loginnavigationapp.core.presentation.screens.LoadingScreen
import com.sebastiannarvaez.loginnavigationapp.feature.auth.presentation.AuthStatus
import com.sebastiannarvaez.loginnavigationapp.feature.auth.presentation.AuthViewModel

inline fun <reified T : Any> NavGraphBuilder.authComposable(
    navController: NavController,
    authVM: AuthViewModel,
    deepLinks: List<NavDeepLink> = emptyList(),
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable<T>(deepLinks = deepLinks) { navBackStackEntry ->
        val authStatus by authVM.authStatusStream.collectAsStateWithLifecycle()

        when (authStatus) {
            AuthStatus.Authenticated -> {
                content(navBackStackEntry)
            }

            AuthStatus.Unauthenticated -> {
                //aqui pudiesemos mostrar una pantalla que diga algo como "para ver este contenido
                // necesitas iniciar sesion" o algo asi, para este ejemplo lo manejamos automaticamente
                LaunchedEffect(authStatus) {
                    //navegar al login
                    navController.navigate(Destinations.Login) {
                        //eliminar todas las pantallas del stack, inclusive en la que esta actualmente
                        //que es la que necesita autenticacion

//                        popUpTo(navBackStackEntry.destination.route!!) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                }
            }

            AuthStatus.Checking -> LoadingScreen()
        }
    }
}