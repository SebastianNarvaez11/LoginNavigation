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

fun validateName(name: String): String? {
    return when {
        name.trim().isEmpty() -> "El nombre es requerido"
        else -> null
    }
}

fun validateBalance(input: String): String? {
    if (input.isBlank()) return "El campo es requerido"

    val number = input.toDoubleOrNull()

    return when {
        number == null -> "Ingresa un número válido"

        number < 0 -> "El saldo no puede ser negativo"

        else -> null
    }
}