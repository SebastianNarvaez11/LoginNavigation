package com.sebastiannarvaez.loginnavigationapp.feature.expenses.presentation.wallets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sebastiannarvaez.loginnavigationapp.core.presentation.models.FieldState
import com.sebastiannarvaez.loginnavigationapp.core.presentation.utils.validateBalance
import com.sebastiannarvaez.loginnavigationapp.core.presentation.utils.validateName
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.models.CreateWalletModel
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.models.UpdateWalletModel
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.models.WalletModel
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.usecase.wallets.CreateWalletUseCase
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.usecase.wallets.DeleteWalletUseCase
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.usecase.wallets.GetAllWalletsUseCase
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.usecase.wallets.UpdateWalletUseCase
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.usecase.wallets.WalletsStreamUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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
    private val createWalletUseCase: CreateWalletUseCase,
    private val updateWalletUseCase: UpdateWalletUseCase,
    private val deleteWalletUseCase: DeleteWalletUseCase
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
        stopDeleteMode()
        _uiState.update { it.copy(selectedWallet = wallet) }
        _formState.update {
            WalletFormState(
                name = FieldState(value = wallet.name),
                balance = FieldState(value = if (wallet.balance == 0.0) "" else wallet.balance.toString())
            )
        }
    }

    fun resetSelectedWallet() {
        _uiState.update { it.copy(selectedWallet = null, updateError = null) }
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
        stopDeleteMode()
    }

    fun resetMessage() {
        _uiState.update { it.copy(message = null) }
    }

    private fun validateForm() {
        handleNameFocusChange()
        handleBalanceFocusChange()
    }

    private var deleteTimerJob: Job? = null

    fun startDeleteMode() {
        deleteTimerJob?.cancel()
        _uiState.update { it.copy(isDeleteModeActive = true) }
        startHideDeleteMode()
    }

    private fun startHideDeleteMode() {
        deleteTimerJob = viewModelScope.launch {
            delay(5000)
            _uiState.update { it.copy(isDeleteModeActive = false) }
        }
    }

    private fun stopDeleteMode() {
        deleteTimerJob?.cancel()
        _uiState.update { it.copy(isDeleteModeActive = false) }
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

    fun onCreateWallet() {
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

    fun onUpdateWallet() {
        validateForm()

        if (_formState.value.isValidForm && _uiState.value.selectedWallet != null) {
            val walletToUpdate = UpdateWalletModel(
                name = _formState.value.name.value, balance = _formState.value.balance.value
            )

            viewModelScope.launch {
                _uiState.update { it.copy(isUpdatingWallet = true) }

                updateWalletUseCase(_uiState.value.selectedWallet?.id ?: "", walletToUpdate)
                    .onSuccess {
                        _uiState.update { it.copy(isUpdatingWallet = false, updateError = null) }
                        resetSelectedWallet()
                    }
                    .onFailure { e ->
                        _uiState.update {
                            it.copy(
                                updateError = e.message,
                                isUpdatingWallet = false
                            )
                        }
                    }
            }
        } else {
            val errors = _formState.value.getErrors()
            _uiState.update { it.copy(message = errors[0]) }
        }
    }

    fun onDeleteWallet(wallet: WalletModel) {
        deleteTimerJob?.cancel()

        viewModelScope.launch {
            _uiState.update { it.copy(isDeletingWallet = true) }
            deleteWalletUseCase(wallet)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isDeletingWallet = false,
                            isDeleteModeActive = false,
                            message = "Billetera eliminada con exito :)"
                        )
                    }
                }.onFailure { e ->
                    _uiState.update {
                        it.copy(
                            isDeletingWallet = false,
                            isDeleteModeActive = false,
                            error = e.message
                        )
                    }
                }
        }
    }
}