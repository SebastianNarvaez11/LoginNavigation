package com.sebastiannarvaez.loginnavigationapp.core.presentation.models

data class FieldState(
    val value: String = "",
    val error: String? = null,
    val hasBeenTouched: Boolean = false
) {
    val shouldShowError: Boolean get() = error != null && hasBeenTouched
}
