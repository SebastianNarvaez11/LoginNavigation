package com.sebastiannarvaez.loginnavigationapp.feature.expenses.presentation.wallets

import com.sebastiannarvaez.loginnavigationapp.core.presentation.models.FieldState

data class WalletFormState(
    //form state
    val name: FieldState = FieldState(),
    val balance: FieldState = FieldState(),

    //other states
    val isLoading: Boolean = false,
    val error: String? = null,

    //events
    val isLoginSuccess: Boolean = false,
    val message: String? = null
) {
    val isValidForm: Boolean
        get() = name.error == null && balance.error == null

    fun getErrors(): List<String> {
        val errors: MutableList<String> = mutableListOf()
        if (name.error != null) errors.add(name.error)
        if (balance.error != null) errors.add(balance.error)
        return errors.toList()
    }
}
