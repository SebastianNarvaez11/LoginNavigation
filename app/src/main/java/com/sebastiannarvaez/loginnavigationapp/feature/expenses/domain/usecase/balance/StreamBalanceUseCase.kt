package com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.usecase.balance

import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.repository.BalanceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StreamBalanceUseCase @Inject constructor(private val balanceRepository: BalanceRepository) {
    operator fun invoke(): Flow<Double> {
        return balanceRepository.getBalance()
    }
}