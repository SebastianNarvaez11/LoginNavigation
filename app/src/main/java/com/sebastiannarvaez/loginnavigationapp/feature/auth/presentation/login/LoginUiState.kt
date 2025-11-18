package com.sebastiannarvaez.loginnavigationapp.feature.auth.presentation.login

import com.sebastiannarvaez.loginnavigationapp.core.presentation.models.FieldState

data class LoginUiState(
    //form state
    val email: FieldState = FieldState(value = "kokoloresas.dp@gmail.com"),
    val password: FieldState = FieldState(value = "tatannvrz"),

    //other states
    val isLoading: Boolean = false,
    val error: String? = null,

    //events
    val isLoginSuccess: Boolean = false,
    val message: String? = null
) {
    val isValidForm: Boolean
        get() = email.error == null && password.error == null

    fun getErrors(): List<String> {
        val errors: MutableList<String> = mutableListOf()
        if (email.error != null) errors.add(email.error)
        if (password.error != null) errors.add(password.error)
        return errors.toList()
    }
}
