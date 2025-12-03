package com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.usecase

import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.models.WalletModel
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.repository.WalletsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WalletsStreamUseCase @Inject constructor(private val walletsRepository: WalletsRepository) {
    operator fun invoke(): Flow<List<WalletModel>> {
        return walletsRepository.walletsStream()
    }
}