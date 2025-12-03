package com.sebastiannarvaez.loginnavigationapp.feature.expenses.presentation.wallets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sebastiannarvaez.loginnavigationapp.core.presentation.models.FieldState
import com.sebastiannarvaez.loginnavigationapp.core.presentation.utils.validateBalance
import com.sebastiannarvaez.loginnavigationapp.core.presentation.utils.validateName
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.models.CreateWalletModel
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.models.WalletModel
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.usecase.CreateWalletUseCase
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.usecase.GetAllWalletsUseCase
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.usecase.WalletsStreamUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val walletsStreamUseCase: WalletsStreamUseCase,
    private val getAllWalletsUseCase: GetAllWalletsUseCase,
    private val createWalletUseCase: CreateWalletUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(WalletUiState())
    var uiState = _uiState.asStateFlow()

    private val _formState = MutableStateFlow(WalletFormState())
    var formState = _formState.asStateFlow()

    val walletsStream = walletsStreamUseCase()
        .catch { e -> _uiState.update { it.copy(error = e.message) } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        onLoadWallets()
    }

    fun onSelectWallet(wallet: WalletModel) {
        _uiState.update { it.copy(selectedWallet = wallet) }
        _formState.update {
            WalletFormState(
                name = FieldState(value = wallet.name),
                balance = FieldState(value = if (wallet.balance == 0.0) "" else wallet.balance.toString())
            )
        }
    }

    fun resetSelectedWallet() {
        _uiState.update { it.copy(selectedWallet = null) }
        _formState.update { WalletFormState() }
    }

    fun handleNameChange(value: String) {
        val error = validateName(value)
        _formState.update {
            it.copy(name = it.name.copy(value = value, error = error))
        }
    }

    fun handleNameFocusChange() {
        val error = validateName(_formState.value.name.value)
        _formState.update { it.copy(name = it.name.copy(hasBeenTouched = true, error = error)) }
    }

    fun handleBalanceChange(value: String) {
        val error = validateBalance(value)
        _formState.update {
            it.copy(balance = it.balance.copy(value = value, error = error))
        }
    }

    fun handleBalanceFocusChange() {
        val error = validateBalance(_formState.value.balance.value)
        _formState.update {
            it.copy(
                balance = it.balance.copy(
                    hasBeenTouched = true, error = error
                )
            )
        }
    }

    fun setShowAddWallet(value: Boolean) {
        _uiState.update { it.copy(showAddWallet = value, createError = null) }
        _formState.update { WalletFormState() }
    }

    fun resetMessage() {
        _uiState.update { it.copy(message = null) }
    }

    private fun validateForm() {
        handleNameFocusChange()
        handleBalanceFocusChange()
    }


    private fun onLoadWallets() {
        viewModelScope.launch {
            _uiState.update { it.copy(isFetchingWallets = true) }

            getAllWalletsUseCase()
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isFetchingWallets = false, error = null
                        )
                    }
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(
                            isFetchingWallets = false, error = e.message
                        )
                    }
                }
        }
    }

    fun createWallet() {
        validateForm()

        if (_formState.value.isValidForm) {
            val newWallet = CreateWalletModel(
                name = _formState.value.name.value, balance = _formState.value.balance.value
            )

            viewModelScope.launch {
                _uiState.update { it.copy(isCreatingWallet = true) }

                createWalletUseCase(newWallet)
                    .onSuccess {
                        _uiState.update { it.copy(isCreatingWallet = false, createError = null) }
                        setShowAddWallet(false)
                    }
                    .onFailure { e ->
                        _uiState.update {
                            it.copy(
                                createError = e.message,
                                isCreatingWallet = false
                            )
                        }
                    }
            }
        } else {
            val errors = _formState.value.getErrors()
            _uiState.update { it.copy(message = errors[0]) }
        }
    }
}