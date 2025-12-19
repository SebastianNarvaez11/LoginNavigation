package com.sebastiannarvaez.loginnavigationapp.feature.expenses.data.remote.api

import com.sebastiannarvaez.loginnavigationapp.feature.expenses.data.remote.requests.CreateWalletRequest
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.data.remote.requests.UpdateWalletRequest
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.data.remote.responses.WalletDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface WalletsApiService {
    @GET("/rest/v1/wallets")
    suspend fun getAllWallets(
        @Query("select", encoded = true) select: String = "*"
    ): List<WalletDto>

    @POST("/rest/v1/wallets")
    @Headers("Prefer: return=representation")
    suspend fun createWallet(@Body wallet: CreateWalletRequest): List<WalletDto>

    @PATCH("/rest/v1/wallets")
    @Headers("Prefer: return=representation")
    suspend fun updateWallet(
        @Query(value = "id", encoded = true) id: String,
        @Body wallet: UpdateWalletRequest
    ): List<WalletDto>

    @DELETE("/rest/v1/wallets")
    suspend fun deleteWallet(@Query("id", encoded = true) walletId: String)
}