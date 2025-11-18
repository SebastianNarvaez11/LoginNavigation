package com.sebastiannarvaez.loginnavigationapp.core.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

sealed class Destinations {
    //pantallas de auth
    @Serializable
    data object Login : Destinations()

    @Serializable
    data object Register : Destinations()

    //pantallas que necesitan auth:
    //con bottonbar
    @Serializable
    data object Home : Destinations()

    @Serializable
    data object Profile : Destinations()

    //sin botton bar
    @Serializable
    data class PostDetail(val id: String) : Destinations()
}

//aqui se definen la pantallas principales, es decir las topLevelDestinations, las que llevan el botton bar
val topLevelDestinations = listOf(
    Destinations.Home::class,
    Destinations.Profile::class
)


sealed class BottomBarItem(
    val route: Destinations,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarItem(Destinations.Home, "Inicio", Icons.Default.Home)
    object Profile : BottomBarItem(Destinations.Profile, "Perfil", Icons.Default.Person)
}

