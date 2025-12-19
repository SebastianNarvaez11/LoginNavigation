package com.sebastiannarvaez.loginnavigationapp.feature.expenses.data.remote.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateWalletRequest(
    @SerialName("name") val name: String? = null,
    @SerialName("balance") val balance: Double? = null
)
