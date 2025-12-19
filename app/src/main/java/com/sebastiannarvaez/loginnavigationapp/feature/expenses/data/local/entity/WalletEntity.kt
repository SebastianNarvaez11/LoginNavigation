package com.sebastiannarvaez.loginnavigationapp.feature.expenses.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.models.WalletModel

@Entity(
    tableName = "wallets",
    indices = [
        Index(value = ["name"], unique = true)
    ]
)
data class WalletEntity(
    @PrimaryKey val id: String,
    val name: String,
    val balance: Double,
)

fun WalletEntity.toDomain(): WalletModel {
    return WalletModel(
        id = id,
        name = name,
        balance = balance
    )
}