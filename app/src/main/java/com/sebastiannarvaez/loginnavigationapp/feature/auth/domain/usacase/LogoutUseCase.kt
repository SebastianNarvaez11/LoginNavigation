package com.sebastiannarvaez.loginnavigationapp.feature.auth.domain.usacase

import com.sebastiannarvaez.loginnavigationapp.feature.auth.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): Result<Unit> {
        return authRepository.logout()
    }
}