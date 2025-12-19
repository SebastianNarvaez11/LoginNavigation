package com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.usecase.wallets

import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.models.CreateWalletModel
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.repository.WalletsRepository
import javax.inject.Inject

class CreateWalletUseCase @Inject constructor(private val walletsRepository: WalletsRepository) {
    suspend operator fun invoke(wallet: CreateWalletModel): Result<Unit> {
        return walletsRepository.createWallet(wallet)
    }
}