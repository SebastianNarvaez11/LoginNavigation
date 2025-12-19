package com.sebastiannarvaez.loginnavigationapp.feature.expenses.presentation.expenses.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ExpenseItemCard() {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 4.dp, vertical = 10.dp),
    ) {
        Checkbox(checked = true, onCheckedChange = {})

        Column() {
            Text(text = "Mercado")
            Text(text = "$ 200.000", fontWeight = FontWeight.ExtraBold)
        }
    }
}