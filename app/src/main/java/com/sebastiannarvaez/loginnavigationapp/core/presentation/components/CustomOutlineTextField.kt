package com.sebastiannarvaez.loginnavigationapp.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle

@Composable
fun CustomOutlineTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (value: String) -> Unit,
    placeholder: String = "",
    isError: Boolean,
    supportingText: String?,
    onFocusChanged: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions()
) {
    var hadFocus by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                if (hadFocus && !focusState.isFocused) {
                    onFocusChanged?.invoke()
                }
                hadFocus = focusState.isFocused
            },
        label = { Text(placeholder) },
        isError = isError,
        supportingText = {
            if (isError && supportingText != null) {
                Text(supportingText)
            }
        },
        keyboardOptions = keyboardOptions,
        textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground)
    )
}