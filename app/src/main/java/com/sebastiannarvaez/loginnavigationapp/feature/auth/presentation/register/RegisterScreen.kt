package com.sebastiannarvaez.loginnavigationapp.feature.auth.presentation.register

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun RegisterScreen(navigateBack: () -> Unit) {
    Column {
        Text(text = "Register")

        Button(onClick = { navigateBack() }) {
            Text("regresar")
        }
    }
}