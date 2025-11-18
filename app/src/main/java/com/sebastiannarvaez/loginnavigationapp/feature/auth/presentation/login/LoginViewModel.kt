package com.sebastiannarvaez.loginnavigationapp.feature.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sebastiannarvaez.loginnavigationapp.core.presentation.utils.validateEmail
import com.sebastiannarvaez.loginnavigationapp.core.presentation.utils.validatePassword
import com.sebastiannarvaez.loginnavigationapp.feature.auth.domain.usacase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun handleEmailChange(value: String) {
        val error = validateEmail(value)
        _uiState.update {
            it.copy(email = it.email.copy(value = value, error = error))
        }
    }

    fun handleEmailFocusChange() {
        val error = validateEmail(_uiState.value.email.value)
        _uiState.update { it.copy(email = it.email.copy(hasBeenTouched = true, error = error)) }
    }

    fun handlePasswordChange(value: String) {
        val error = validatePassword(value)
        _uiState.update {
            it.copy(password = it.password.copy(value = value, error = error))
        }
    }

    fun handlePasswordFocusChange() {
        val error = validatePassword(_uiState.value.password.value)
        _uiState.update {
            it.copy(
                password = it.password.copy(
                    hasBeenTouched = true,
                    error = error
                )
            )
        }
    }

    private fun validateForm() {
        handleEmailFocusChange()
        handlePasswordFocusChange()
    }

    fun handleSubmitLogin() {
        validateForm()

        if (_uiState.value.isValidForm) {
            _uiState.update { it.copy(isLoading = true) }

            viewModelScope.launch {
                loginUseCase(
                    email = _uiState.value.email.value,
                    password = _uiState.value.password.value
                )
                    .onSuccess {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = null,
                                isLoginSuccess = true
                            )
                        }
                    }
                    .onFailure { e ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = e.message ?: "Error al hacer login"
                            )
                        }
                    }
            }
        } else {
            val errors = _uiState.value.getErrors()
            _uiState.update { it.copy(message = errors[0]) }
        }
    }

    fun resetIsLoginSuccess() {
        _uiState.update { it.copy(isLoginSuccess = false) }
    }

    fun resetMessage() {
        _uiState.update { it.copy(message = null) }
    }
}