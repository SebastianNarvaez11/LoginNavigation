package com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.usecase.wallets

import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.repository.WalletsRepository
import javax.inject.Inject

class GetAllWalletsUseCase @Inject constructor(private val walletsRepository: WalletsRepository) {
    suspend operator fun invoke(): Result<Unit> {
        return walletsRepository.getAllWallets()
    }
}