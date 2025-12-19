package com.sebastiannarvaez.loginnavigationapp.feature.expenses.data.repository

import com.sebastiannarvaez.loginnavigationapp.feature.expenses.data.local.dao.BalanceDao
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.repository.BalanceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BalanceRepositoryImp @Inject constructor(
    private val balanceDao: BalanceDao
) : BalanceRepository {

    override fun getBalance(): Flow<Double> {
        return balanceDao.getBalance()
    }
}