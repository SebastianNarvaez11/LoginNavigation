package com.sebastiannarvaez.loginnavigationapp.feature.expenses.presentation.wallets

import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.models.WalletModel

data class WalletUiState(
    val selectedWallet: WalletModel? = null,
    val showAddWallet: Boolean = false,

    val isFetchingWallets: Boolean = false, //remote
    val isCreatingWallet: Boolean = false,

    val message: String? = null,

    val error: String? = null,
    val createError: String? = null
)
