package com.sebastiannarvaez.loginnavigationapp.feature.auth.presentation

import com.sebastiannarvaez.loginnavigationapp.feature.auth.domain.models.UserModel

data class AuthUiState(
    val user: UserModel? = null,
    val error: String? = null
)