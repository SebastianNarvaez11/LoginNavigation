package com.sebastiannarvaez.loginnavigationapp.feature.expenses.presentation.wallets.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.models.WalletModel
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.presentation.wallets.WalletFormState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletList(
    wallets: List<WalletModel>,
    isFetchingWallets: Boolean = false,
    isUpdatingWallet: Boolean = false,
    updateWalletError: String? = null,
    formState: WalletFormState,
    selectedWallet: WalletModel?,
    onPressWallet: (wallet: WalletModel) -> Unit,
    onCollapseWallet: () -> Unit,
    onNameChange: (value: String) -> Unit,
    onNameFocusChange: () -> Unit,
    onBalanceChange: (value: String) -> Unit,
    onBalanceFocusChange: () -> Unit,
    onSave: () -> Unit,
    onLongPressWallet: () -> Unit,
    isDeleteModeActive: Boolean = false,
    isDeletingWallet: Boolean = false,
    onDelete: (wallet: WalletModel) -> Unit,
) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val expandedWidth = screenWidth - 32.dp
    val collapsedWidth = 140.dp

    val animatedSpacing by animateDpAsState(
        targetValue = if (selectedWallet == null) 15.dp else 0.dp
    )

    val animatedOpacity by animateFloatAsState(
        targetValue = if (isFetchingWallets || isDeletingWallet) 1f else 0f
    )

    Column() {
        AnimatedVisibility(selectedWallet == null) {
            LinearProgressIndicator(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
                    .alpha(animatedOpacity)
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(animatedSpacing),
            contentPadding = PaddingValues(horizontal = 16.dp),
        ) {
            items(wallets, key = { it.id }) { wallet ->

                val isSelected = selectedWallet == wallet

                val animatedWidth by animateDpAsState(
                    targetValue = when {
                        isSelected -> expandedWidth
                        (!isSelected && selectedWallet != null) -> 0.dp
                        else -> collapsedWidth
                    }
                )

                WalletItemCard(
                    wallet = wallet,
                    isSelected = isSelected,
                    onPressWallet = onPressWallet,
                    onLongPressWallet = onLongPressWallet,
                    modifier = Modifier.width(animatedWidth),
                    onDelete = onDelete,
                    isDeleteModeActive = isDeleteModeActive,
                    content = {
                        AnimatedVisibility(isSelected) {
                            WalletForm(
                                titleForm = "Actualizar billetera",
                                formState = formState,
                                onClose = onCollapseWallet,
                                onNameChange = onNameChange,
                                onNameFocusChange = onNameFocusChange,
                                onBalanceChange = onBalanceChange,
                                onBalanceFocusChange = onBalanceFocusChange,
                                onSave = { onSave() },
                                isLoading = isUpdatingWallet,
                                error = updateWalletError,
                            )
                        }
                    }
                )
            }
        }
    }
}

