package com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.repository

import kotlinx.coroutines.flow.Flow

interface BalanceRepository {
    fun getBalance(): Flow<Double>
}