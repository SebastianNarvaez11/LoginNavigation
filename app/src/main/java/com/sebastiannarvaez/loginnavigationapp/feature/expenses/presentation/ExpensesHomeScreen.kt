package com.sebastiannarvaez.loginnavigationapp.feature.expenses.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.presentation.balance.BalanceViewModel
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.presentation.balance.components.BalanceCard
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.presentation.expenses.components.CreateExpenseFAB
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.presentation.expenses.components.ExpenseItemCard
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.presentation.wallets.components.WalletList
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.presentation.wallets.WalletViewModel
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.presentation.wallets.components.WalletForm

@Composable
fun ExpensesHomeScreen(
    balanceVM: BalanceViewModel = hiltViewModel(),
    walletVM: WalletViewModel = hiltViewModel(),
    onShowSnackbar: suspend (message: String) -> Unit
) {
    val balanceUiState by balanceVM.uiState.collectAsStateWithLifecycle()
    val balance by balanceVM.balanceStream.collectAsStateWithLifecycle()

    val walletUiState by walletVM.uiState.collectAsStateWithLifecycle()
    val walletFormState by walletVM.formState.collectAsStateWithLifecycle()

    val wallets by walletVM.walletsStream.collectAsStateWithLifecycle()

    LaunchedEffect(walletUiState.message) {
        walletUiState.message?.let {
            onShowSnackbar(it)
            walletVM.resetMessage()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            BalanceCard(balance = balance)

            AnimatedVisibility(!walletUiState.showAddWallet && walletUiState.selectedWallet == null) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 0.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Tus billeteras:")

                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(onClick = { walletVM.setShowAddWallet(true) }) {
                        Icon(Icons.Default.Add, contentDescription = "nueva wallet")
                    }
                }
            }

            AnimatedVisibility(walletUiState.error != null) {
                Text(
                    text = walletUiState.error ?: "Ocurrio un error",
                    color = MaterialTheme.colorScheme.error
                )
            }

            AnimatedContent(walletUiState.showAddWallet) { value ->
                when (value) {
                    true -> {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .shadow(
                                    elevation = 10.dp,
                                    clip = true,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(16.dp)
                        ) {
                            WalletForm(
                                titleForm = "Nueva billetera",
                                formState = walletFormState,
                                onNameChange = walletVM::handleNameChange,
                                onNameFocusChange = walletVM::handleNameFocusChange,
                                onBalanceChange = walletVM::handleBalanceChange,
                                onBalanceFocusChange = walletVM::handleBalanceFocusChange,
                                onClose = { walletVM.setShowAddWallet(false) },
                                onSave = walletVM::onCreateWallet,
                                isLoading = walletUiState.isCreatingWallet,
                                error = walletUiState.createError
                            )
                        }
                    }

                    false -> {
                        WalletList(
                            wallets = wallets,
                            formState = walletFormState,
                            isFetchingWallets = walletUiState.isFetchingWallets,
                            selectedWallet = walletUiState.selectedWallet,
                            onPressWallet = walletVM::onSelectWallet,
                            onCollapseWallet = walletVM::resetSelectedWallet,
                            onNameChange = walletVM::handleNameChange,
                            onNameFocusChange = walletVM::handleNameFocusChange,
                            onBalanceChange = walletVM::handleBalanceChange,
                            onBalanceFocusChange = walletVM::handleBalanceFocusChange,
                            onSave = walletVM::onUpdateWallet,
                            isUpdatingWallet = walletUiState.isUpdatingWallet,
                            updateWalletError = walletUiState.updateError,
                            onLongPressWallet = walletVM::startDeleteMode,
                            onDelete = walletVM::onDeleteWallet,
                            isDeleteModeActive = walletUiState.isDeleteModeActive,
                            isDeletingWallet = walletUiState.isDeletingWallet
                        )
                    }
                }
            }
        }
        ExpenseItemCard()

        CreateExpenseFAB(modifier = Modifier.align(Alignment.BottomEnd))
    }
}