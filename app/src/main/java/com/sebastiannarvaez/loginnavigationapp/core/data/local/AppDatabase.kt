package com.sebastiannarvaez.loginnavigationapp.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.data.local.dao.BalanceDao
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.data.local.dao.WalletDao
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.data.local.entity.WalletEntity
import com.sebastiannarvaez.loginnavigationapp.feature.posts.data.local.dao.PostDao
import com.sebastiannarvaez.loginnavigationapp.feature.posts.data.local.entity.PostEntity

@Database(entities = [PostEntity::class, WalletEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao
    abstract fun walletDao(): WalletDao
    abstract fun balanceDao(): BalanceDao
}