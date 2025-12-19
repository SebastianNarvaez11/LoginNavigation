package com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.usecase.wallets

import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.models.WalletModel
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.repository.WalletsRepository
import javax.inject.Inject

class DeleteWalletUseCase @Inject constructor(private val walletsRepository: WalletsRepository) {
    suspend operator fun invoke(wallet: WalletModel): Result<Unit> {
        return walletsRepository.deleteWallet(wallet)
    }
}