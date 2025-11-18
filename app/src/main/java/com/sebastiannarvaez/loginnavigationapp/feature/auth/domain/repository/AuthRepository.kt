package com.sebastiannarvaez.loginnavigationapp.feature.auth.domain.repository

import com.sebastiannarvaez.loginnavigationapp.feature.auth.presentation.AuthStatus
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun statusStream(): Flow<AuthStatus>

    suspend fun login(email: String, password: String): Result<Unit>

    suspend fun logout(): Result<Unit>
}