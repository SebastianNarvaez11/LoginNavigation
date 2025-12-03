package com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.models

data class WalletModel(
    val id: String,
    val name: String,
    val balance: Double = 0.0
)