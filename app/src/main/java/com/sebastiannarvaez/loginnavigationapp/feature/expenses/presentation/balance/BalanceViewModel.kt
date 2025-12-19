package com.sebastiannarvaez.loginnavigationapp.feature.expenses.presentation.balance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.usecase.balance.StreamBalanceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BalanceViewModel @Inject constructor(
    private val streamBalanceUseCase: StreamBalanceUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(BalanceUiState())
    val uiState = _uiState.asStateFlow()

    val balanceStream: StateFlow<Double> = streamBalanceUseCase()
        .catch { e -> _uiState.update { it.copy(error = e.message) } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0.0
        )
}