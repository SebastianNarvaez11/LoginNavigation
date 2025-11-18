package com.sebastiannarvaez.loginnavigationapp.feature.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sebastiannarvaez.loginnavigationapp.feature.auth.domain.usacase.LogoutUseCase
import com.sebastiannarvaez.loginnavigationapp.feature.auth.domain.usacase.StreamAuthStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val streamAuthStatusUseCase: StreamAuthStatusUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState = _uiState.asStateFlow()

    val authStatusStream: StateFlow<AuthStatus> = streamAuthStatusUseCase()
        .catch { e -> emit(AuthStatus.Unauthenticated) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AuthStatus.Checking
        )


    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
                .onFailure { e ->
                    _uiState.update {
                        it.copy(
                            error = e.message ?: "Error al hacer logout"
                        )
                    }
                }
        }
    }
}