package com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.usecase.wallets

import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.models.UpdateWalletModel
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.repository.WalletsRepository
import javax.inject.Inject

class UpdateWalletUseCase @Inject constructor(private val walletsRepository: WalletsRepository) {
    suspend operator fun invoke(walletId: String, wallet: UpdateWalletModel): Result<Unit> {
        return walletsRepository.updateWallet(walletId, wallet)
    }
}