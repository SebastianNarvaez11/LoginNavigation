package com.sebastiannarvaez.loginnavigationapp.feature.expenses.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BalanceCard() {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.primary)
            .clickable {
                isExpanded = !isExpanded
            }
            .animateContentSize()
            .padding(16.dp)
    ) {
        Text(text = "Total disponible", color = MaterialTheme.colorScheme.onPrimary)
        Text(
            text = "$ 56,000,000",
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 30.sp
        )

        AnimatedVisibility(isExpanded) {
            Column() {
                Text(text = "Total de gatos", color = MaterialTheme.colorScheme.onPrimary)
                Text(
                    text = "Probable saldo final",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}