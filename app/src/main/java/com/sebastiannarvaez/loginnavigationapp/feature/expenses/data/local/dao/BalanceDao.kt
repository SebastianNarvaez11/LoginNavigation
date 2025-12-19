package com.sebastiannarvaez.loginnavigationapp.feature.expenses.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BalanceDao {
    @Query("SELECT SUM(balance) FROM wallets")
    fun getBalance(): Flow<Double>
}