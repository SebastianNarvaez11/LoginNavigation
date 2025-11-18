package com.sebastiannarvaez.loginnavigationapp.core.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.toRoute
import com.sebastiannarvaez.loginnavigationapp.core.presentation.home.HomeScreen
import com.sebastiannarvaez.loginnavigationapp.core.presentation.screens.ProfileScreen
import com.sebastiannarvaez.loginnavigationapp.feature.auth.presentation.AuthViewModel
import com.sebastiannarvaez.loginnavigationapp.feature.posts.presentation.detail.PostDetailScreen

fun NavGraphBuilder.mainGraph(navController: NavController, authVM: AuthViewModel) {
    authComposable<Destinations.Home>(navController, authVM) {
        HomeScreen(
            navigateToDetail = { id -> navController.navigate(Destinations.PostDetail(id)) },
            logout = { authVM.logout() }
        )
    }

    authComposable<Destinations.PostDetail>(navController, authVM) { navBackStackEntry ->
        val id = navBackStackEntry.toRoute<Destinations.PostDetail>().id
        PostDetailScreen(navigateBack = { navController.popBackStack() }, id)
    }

    authComposable<Destinations.Profile>(navController, authVM) {
        ProfileScreen(navigateBack = { navController.popBackStack() })
    }
}