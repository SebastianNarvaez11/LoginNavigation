package com.sebastiannarvaez.loginnavigationapp.core.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navDeepLink
import com.sebastiannarvaez.loginnavigationapp.core.presentation.home.HomeScreen
import com.sebastiannarvaez.loginnavigationapp.core.presentation.screens.ProfileScreen
import com.sebastiannarvaez.loginnavigationapp.feature.auth.presentation.AuthViewModel
import com.sebastiannarvaez.loginnavigationapp.feature.posts.presentation.detail.PostDetailScreen


fun NavGraphBuilder.mainGraph(navController: NavController, authVM: AuthViewModel) {

    authComposable<Destinations.Home>(navController, authVM) {
        HomeScreen(
            navigateToPostDetail = { id -> navController.navigate(Destinations.PostDetail(id)) },
            logout = { authVM.logout() }
        )
    }

    authComposable<Destinations.PostDetail>(
        navController,
        authVM,
        // 🔗 Deep Link Type-Safe:
        // No es necesario usar uriPattern con placeholders (ej: ".../posts/{id}").
        // Al usar la variante genérica `<Destinations.PostDetail>`, la librería inspecciona
        // la data class, detecta el campo `val id: String` y genera el regex automáticamente.
        // lo que venga después del `basePath` se asignará a `id`.
        deepLinks = listOf(
            navDeepLink<Destinations.PostDetail>(basePath = "https://www.sebastian.com/posts")
        )
    ) { navBackStackEntry ->

        // 🧠 Arquitectura Limpia (Decoupling):
        // NO extraemos el argumento aquí (`navBackStackEntry.toRoute<...>().id`) para pasarlo
        // por parámetros al Composable.
        //
        // Razón: Dejamos que el `PostDetailViewModel` sea quien recupere el dato directamente
        // usando `SavedStateHandle`. Esto mantiene la firma de `PostDetailScreen` limpia
        // y facilita el testing (la UI no depende de argumentos de navegación).

        PostDetailScreen(navigateBack = { navController.popBackStack() })
    }

    authComposable<Destinations.Profile>(navController, authVM) {
        ProfileScreen(navigateBack = { navController.popBackStack() })
    }
}