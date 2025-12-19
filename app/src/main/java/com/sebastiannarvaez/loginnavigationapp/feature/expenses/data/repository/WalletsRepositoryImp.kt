package com.sebastiannarvaez.loginnavigationapp.feature.expenses.data.repository

import com.sebastiannarvaez.loginnavigationapp.core.domain.models.DomainError
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.data.local.dao.WalletDao
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.data.local.entity.toDomain
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.data.remote.api.WalletsApiService
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.data.remote.responses.toEntity
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.models.CreateWalletModel
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.models.UpdateWalletModel
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.models.WalletModel
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.models.toEntity
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.models.toRequest
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.repository.WalletsRepository
import com.sebastiannarvaez.todoappofflinefirst.data.utils.ErrorMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WalletsRepositoryImp @Inject constructor(
    private val walletApi: WalletsApiService,
    private val walletDao: WalletDao,
    private val errorMapper: ErrorMapper
) : WalletsRepository {

    override fun walletsStream(): Flow<List<WalletModel>> {
        return walletDao.getAllWallets()
            .map { walletEntities -> walletEntities.map { it.toDomain() } }
    }

    override suspend fun getAllWallets(): Result<Unit> {
        try {
            val wallets = walletApi.getAllWallets().map { it.toEntity() }
            walletDao.deleteAllWallets() //clean old cache
            walletDao.insertManyWallets(wallets)
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(errorMapper.map(e))
        }
    }

    override suspend fun createWallet(wallet: CreateWalletModel): Result<Unit> {
        try {
            val result = walletApi.createWallet(wallet.toRequest())

            if (result.isEmpty()) {
                return Result.failure(DomainError.ApiError("No se devolvio la wallet creada :("))
            }

            walletDao.insertManyWallets(result.map { it.toEntity() })

            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(errorMapper.map(e))
        }
    }

    override suspend fun updateWallet(
        walletId: String, wallet: UpdateWalletModel
    ): Result<Unit> {
        try {
            val idToFilter = "eq.$walletId"
            val result = walletApi.updateWallet(id = idToFilter, wallet = wallet.toRequest())

            if (result.isEmpty()) {
                return Result.failure(DomainError.ApiError("Ocurrio un error al actualizar la billetera :("))
            }

            val updatedWallets = result.map { it.toEntity() }
            walletDao.insertManyWallets(updatedWallets)

            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(errorMapper.map(e))
        }
    }

    override suspend fun deleteWallet(wallet: WalletModel): Result<Unit> {
        try {
            val idToFilter = "eq.${wallet.id}"
            walletApi.deleteWallet(idToFilter)
            walletDao.deleteWallet(wallet.toEntity())
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(errorMapper.map(e))
        }
    }
}