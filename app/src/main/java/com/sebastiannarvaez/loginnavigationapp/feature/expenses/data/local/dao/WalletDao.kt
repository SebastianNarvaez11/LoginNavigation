package com.sebastiannarvaez.loginnavigationapp.feature.expenses.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.data.local.entity.WalletEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WalletDao {
    @Query("SELECT * FROM wallets ORDER BY name ASC")
    fun getAllWallets(): Flow<List<WalletEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertManyWallets(wallets: List<WalletEntity>)

    @Query("DELETE FROM wallets")
    suspend fun deleteAllWallets()

    @Delete
    suspend fun deleteWallet(wallet: WalletEntity): Int
}