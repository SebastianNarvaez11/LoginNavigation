package com.sebastiannarvaez.loginnavigationapp.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlin.reflect.KClass

class MyAppState(
    val navController: NavHostController,
    private val topLevelDestinations: List<KClass<out Destinations>>
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState()
            .value
            ?.destination

    val shouldShowBottomBar: Boolean
        @Composable get() = topLevelDestinations.any { destination ->
            currentDestination?.hasRoute(destination) == true
        }
}

@Composable
fun rememberMyAppState(
    navController: NavHostController = rememberNavController()
): MyAppState = remember(navController) {

    MyAppState(
        navController,
        topLevelDestinations = topLevelDestinations
    )
}