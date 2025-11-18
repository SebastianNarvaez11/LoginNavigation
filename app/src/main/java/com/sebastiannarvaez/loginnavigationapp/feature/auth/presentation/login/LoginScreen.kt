package com.sebastiannarvaez.loginnavigationapp.feature.auth.presentation.login

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sebastiannarvaez.loginnavigationapp.core.presentation.components.CustomOutlineTextField

@Composable
fun LoginScreen(
    navigateToHome: () -> Unit,
    onShowSnackbar: suspend (message: String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isLoginSuccess) {
        if (uiState.isLoginSuccess) {
            navigateToHome()
            viewModel.resetIsLoginSuccess()
        }
    }

    LaunchedEffect(uiState.message) {
        uiState.message?.let {
            onShowSnackbar(it)
            viewModel.resetMessage()
        }
    }

    Column {
        Text(text = "Login")

        CustomOutlineTextField(
            value = uiState.email.value,
            onValueChange = { viewModel.handleEmailChange(it) },
            placeholder = "Correo",
            isError = uiState.email.shouldShowError,
            supportingText = uiState.email.error,
            onFocusChanged = { viewModel.handleEmailFocusChange() }
        )

        CustomOutlineTextField(
            value = uiState.password.value,
            onValueChange = { viewModel.handlePasswordChange(it) },
            placeholder = "Contraseña",
            isError = uiState.password.shouldShowError,
            supportingText = uiState.password.error,
            onFocusChanged = { viewModel.handlePasswordFocusChange() }
        )

        AnimatedVisibility(uiState.error != null) {
            Text(text = uiState.error!!, color = Color.Red)
        }

        Button(onClick = { viewModel.handleSubmitLogin() }) {
            if (uiState.isLoading) {
                CircularProgressIndicator(color = Color.Red)
            } else {
                Text("Iniciar sesion")
            }
        }
    }
}