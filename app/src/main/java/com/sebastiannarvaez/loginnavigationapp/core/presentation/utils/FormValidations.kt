package com.sebastiannarvaez.loginnavigationapp.core.presentation.utils

fun validateEmail(email: String): String? {
    return when {
        email.isEmpty() -> "El correo es requerido"
        !email.contains("@") -> "Email no es valido"
        else -> null
    }
}

fun validatePassword(password: String): String? {
    return when {
        password.length < 6 -> "la contraseña es muy corta"
        else -> null
    }
}
