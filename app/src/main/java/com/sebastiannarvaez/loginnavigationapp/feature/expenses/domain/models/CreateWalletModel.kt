package com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.models

import com.sebastiannarvaez.loginnavigationapp.feature.expenses.data.remote.requests.WalletRequest

data class CreateWalletModel(
    val name: String,
    val balance: String
)

fun CreateWalletModel.toRequest(): WalletRequest {
    return WalletRequest(
        name = name,
        balance = balance.toDouble()
    )
}