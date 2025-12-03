package com.sebastiannarvaez.loginnavigationapp.feature.expenses.presentation.expenses.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun CreateExpenseFAB(modifier: Modifier = Modifier) {
    var isExpandedAdd by remember { mutableStateOf(false) }

    var email by remember { mutableStateOf("") }

    val animatedRadius by animateDpAsState(
        targetValue = if (isExpandedAdd) 20.dp else 500.dp
    )

    val animatedColor by animateColorAsState(
        targetValue = if (isExpandedAdd) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.secondary,
        animationSpec = tween(durationMillis = 500)
    )

    Box(
        modifier = modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(animatedRadius))
            .background(animatedColor)
            .animateContentSize()
    ) {
        AnimatedVisibility(!isExpandedAdd) {
            IconButton(onClick = { isExpandedAdd = !isExpandedAdd }) {
                Icon(Icons.Default.Add, contentDescription = "")
            }
        }

        AnimatedVisibility(
            isExpandedAdd, enter = fadeIn(), exit = fadeOut(
                animationSpec = tween(durationMillis = 0)
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
//                        .fillMaxSize()
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Formulario para añadir Gasto",
                    color = MaterialTheme.colorScheme.onSecondary
                )

                IconButton(onClick = { isExpandedAdd = !isExpandedAdd }) {
                    Icon(Icons.Default.Close, contentDescription = "")
                }

                OutlinedTextField(value = email, onValueChange = { email = it })

                Spacer(modifier = Modifier.height(300.dp))
            }
        }
    }
}