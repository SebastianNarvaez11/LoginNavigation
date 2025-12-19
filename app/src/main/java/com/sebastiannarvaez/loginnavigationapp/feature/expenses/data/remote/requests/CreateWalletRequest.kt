package com.sebastiannarvaez.loginnavigationapp.feature.expenses.data.remote.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateWalletRequest(
    @SerialName("name") val name: String,
    @SerialName("balance") val balance: Double
)
