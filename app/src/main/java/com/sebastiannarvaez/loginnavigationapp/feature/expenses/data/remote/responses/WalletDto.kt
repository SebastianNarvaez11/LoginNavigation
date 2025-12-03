package com.sebastiannarvaez.loginnavigationapp.feature.expenses.data.remote.responses

import com.sebastiannarvaez.loginnavigationapp.feature.expenses.data.local.entity.WalletEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WalletDto(
    @SerialName("id") val id: String,
    @SerialName("user_id") val userId: String,
    @SerialName("name") val name: String,
    @SerialName("balance") val balance: Double,
    @SerialName("created_at") val createdAt: String
)

fun WalletDto.toEntity(): WalletEntity {
    return WalletEntity(
        id = id,
        name = name,
        balance = balance,
        createdAt = createdAt
    )
}