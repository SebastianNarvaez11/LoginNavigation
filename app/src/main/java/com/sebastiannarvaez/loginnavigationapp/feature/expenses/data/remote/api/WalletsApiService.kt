package com.sebastiannarvaez.loginnavigationapp.feature.expenses.data.remote.api

import com.sebastiannarvaez.loginnavigationapp.feature.expenses.data.remote.requests.WalletRequest
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.data.remote.responses.WalletDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface WalletsApiService {
    @GET("/rest/v1/wallets")
    suspend fun getAllWallets(
        @Query("select", encoded = true) select: String = "*"
    ): List<WalletDto>

    @POST("/rest/v1/wallets")
    @Headers("Prefer: return=representation")
    suspend fun createWallet(@Body wallet: WalletRequest): List<WalletDto>
}