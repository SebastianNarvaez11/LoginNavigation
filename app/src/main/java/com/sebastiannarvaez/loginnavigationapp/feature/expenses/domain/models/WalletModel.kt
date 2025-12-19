package com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.models

import com.sebastiannarvaez.loginnavigationapp.feature.expenses.data.local.entity.WalletEntity

data class WalletModel(
    val id: String,
    val name: String,
    val balance: Double = 0.0
)

fun WalletModel.toEntity(): WalletEntity {
    return WalletEntity(
        id = id,
        name = name,
        balance = balance
    )
}