package com.sebastiannarvaez.loginnavigationapp.core.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.sebastiannarvaez.loginnavigationapp.core.presentation.home.HomeScreen
import com.sebastiannarvaez.loginnavigationapp.core.presentation.screens.ProfileScreen
import com.sebastiannarvaez.loginnavigationapp.feature.auth.presentation.AuthViewModel
import com.sebastiannarvaez.loginnavigationapp.feature.posts.presentation.detail.PostDetailScreen

fun NavGraphBuilder.mainGraph(navController: NavController, authVM: AuthViewModel) {
    authComposable<Destinations.Home>(navController, authVM) {
        HomeScreen(
            navigateToPostDetail = { id -> navController.navigate(Destinations.PostDetail(id)) }, //aqui si es necesario mandar el parametro
            logout = { authVM.logout() }
        )
    }

    authComposable<Destinations.PostDetail>(navController, authVM) { navBackStackEntry ->
        // ya no es necesario pasar el id como parametro porque lo capturamos en el view model
        // directamnte usando SavedStateHandle, pero si es necesario mandarlo desde la pantalla de origen de la que sale la nevagacion
//        val id = navBackStackEntry.toRoute<Destinations.PostDetail>().id
        PostDetailScreen(navigateBack = { navController.popBackStack() })
    }

    authComposable<Destinations.Profile>(navController, authVM) {
        ProfileScreen(navigateBack = { navController.popBackStack() })
    }
}