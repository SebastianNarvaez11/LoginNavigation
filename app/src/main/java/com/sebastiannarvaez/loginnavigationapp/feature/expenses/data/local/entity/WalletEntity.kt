package com.sebastiannarvaez.loginnavigationapp.feature.expenses.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.models.WalletModel

@Entity(tableName = "wallets")
data class WalletEntity(
    @PrimaryKey val id: String,
    val name: String,
    val balance: Double,
    val createdAt: String
)

fun WalletEntity.toDomain(): WalletModel {
    return WalletModel(
        id = id,
        name = name,
        balance = balance
    )
}