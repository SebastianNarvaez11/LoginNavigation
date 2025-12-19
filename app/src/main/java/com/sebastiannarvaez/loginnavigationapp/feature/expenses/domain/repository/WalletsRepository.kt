package com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.repository

import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.models.CreateWalletModel
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.models.UpdateWalletModel
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.models.WalletModel
import kotlinx.coroutines.flow.Flow

interface WalletsRepository {
    fun walletsStream(): Flow<List<WalletModel>>

    suspend fun getAllWallets(): Result<Unit>

    suspend fun createWallet(wallet: CreateWalletModel): Result<Unit>

    suspend fun updateWallet(walletId: String, wallet: UpdateWalletModel): Result<Unit>

    suspend fun deleteWallet(wallet: WalletModel): Result<Unit>
}