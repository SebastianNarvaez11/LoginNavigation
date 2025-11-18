package com.sebastiannarvaez.loginnavigationapp.feature.posts.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun PostDetailScreen(navigateBack: () -> Unit, id: String) {
    Column() {
        Button(onClick = { navigateBack() }) {
            Text("Regresar")
        }

        Text(text = "Detail screen id: $id")
    }
}
