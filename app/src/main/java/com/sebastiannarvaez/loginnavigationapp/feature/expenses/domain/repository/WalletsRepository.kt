package com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.repository

import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.models.CreateWalletModel
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.models.WalletModel
import kotlinx.coroutines.flow.Flow

interface WalletsRepository {
    fun walletsStream(): Flow<List<WalletModel>>

    suspend fun getAllWallets(): Result<Unit>

    suspend fun createWallet(wallet: CreateWalletModel): Result<Unit>
}