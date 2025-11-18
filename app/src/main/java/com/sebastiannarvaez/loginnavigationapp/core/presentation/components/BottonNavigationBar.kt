package com.sebastiannarvaez.loginnavigationapp.core.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import com.sebastiannarvaez.loginnavigationapp.core.presentation.navigation.BottomBarItem
import com.sebastiannarvaez.loginnavigationapp.core.presentation.navigation.Destinations


@Composable
fun BottomNavigationBar(navController: NavController, currentDestination: NavDestination?) {
    NavigationBar() {
        val bottomBarItems: List<BottomBarItem> = listOf(BottomBarItem.Home, BottomBarItem.Profile)

        bottomBarItems.forEach { item ->
            val isSelected = currentDestination?.hasRoute(item.route::class) == true

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(Destinations.Home) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = {
                    Text(
                        item.title
                    )
                }
            )
        }
    }
}