package com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.models

import com.sebastiannarvaez.loginnavigationapp.feature.expenses.data.remote.requests.CreateWalletRequest

data class CreateWalletModel(
    val name: String,
    val balance: String
)

fun CreateWalletModel.toRequest(): CreateWalletRequest {
    return CreateWalletRequest(
        name = name,
        balance = balance.toDouble()
    )
}