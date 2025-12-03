package com.sebastiannarvaez.loginnavigationapp.core.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen(
    onNavigateToExpenses: () -> Unit
) {
    Column() {
        Text(text = "Home")

        Button(onClick = { onNavigateToExpenses() }) {
            Text(text = "ir a gastos")
        }
    }
}