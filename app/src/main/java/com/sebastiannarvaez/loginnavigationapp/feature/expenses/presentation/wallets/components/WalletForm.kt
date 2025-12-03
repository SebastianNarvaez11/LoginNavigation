package com.sebastiannarvaez.loginnavigationapp.feature.expenses.presentation.wallets.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sebastiannarvaez.loginnavigationapp.core.presentation.components.CustomOutlineTextField
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.presentation.wallets.WalletFormState

@Composable
fun WalletForm(
    titleForm: String,
    formState: WalletFormState,
    onNameChange: (value: String) -> Unit,
    onNameFocusChange: () -> Unit,
    onBalanceChange: (value: String) -> Unit,
    onBalanceFocusChange: () -> Unit,
    onClose: () -> Unit,
    onSave: () -> Unit,
    isLoading: Boolean = false,
    error: String? = null
) {
    Column() {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = titleForm,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 10.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = { onClose() }) {
                Icon(Icons.Default.Close, contentDescription = "")
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        CustomOutlineTextField(
            value = formState.name.value,
            onValueChange = onNameChange,
            placeholder = "Nombre",
            isError = formState.name.shouldShowError,
            supportingText = formState.name.error,
            onFocusChanged = onNameFocusChange
        )

        Spacer(modifier = Modifier.height(5.dp))

        CustomOutlineTextField(
            value = formState.balance.value,
            onValueChange = onBalanceChange,
            placeholder = "Saldo",
            isError = formState.balance.shouldShowError,
            supportingText = formState.balance.error,
            onFocusChanged = onBalanceFocusChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(5.dp))

        AnimatedVisibility(error != null) {
            Text(text = error ?: "Ocurrio un error", color = MaterialTheme.colorScheme.error)
        }

        Button(onClick = { onSave() }, enabled = !isLoading, modifier = Modifier.fillMaxWidth()) {
            AnimatedContent(isLoading) { value ->
                when (value) {
                    true -> CircularProgressIndicator()
                    false -> Text(text = "Guardar")
                }
            }
        }
    }
}