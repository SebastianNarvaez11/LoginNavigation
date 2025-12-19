package com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.models

import com.sebastiannarvaez.loginnavigationapp.feature.expenses.data.remote.requests.UpdateWalletRequest

data class UpdateWalletModel(
    val name: String? = null,
    val balance: String? = null
)

fun UpdateWalletModel.toRequest(): UpdateWalletRequest {
    return UpdateWalletRequest(
        name = name,
        balance = balance?.toDoubleOrNull()
    )
}