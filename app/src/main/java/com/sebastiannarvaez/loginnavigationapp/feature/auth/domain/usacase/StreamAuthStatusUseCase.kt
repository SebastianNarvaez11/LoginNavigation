package com.sebastiannarvaez.loginnavigationapp.feature.auth.domain.usacase

import com.sebastiannarvaez.loginnavigationapp.feature.auth.domain.repository.AuthRepository
import com.sebastiannarvaez.loginnavigationapp.feature.auth.presentation.AuthStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StreamAuthStatusUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(): Flow<AuthStatus> {
        return authRepository.statusStream()
    }
}