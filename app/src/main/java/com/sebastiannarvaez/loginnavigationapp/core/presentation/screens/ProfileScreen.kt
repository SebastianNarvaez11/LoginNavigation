package com.sebastiannarvaez.loginnavigationapp.core.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ProfileScreen(navigateBack: () -> Unit) {
    Column() {
        Text(text = "Perfil de usuario")

        Button(onClick = { navigateBack() }) {
            Text("regresar")
        }
    }
}