package com.sebastiannarvaez.loginnavigationapp.feature.auth.domain.usacase

import com.sebastiannarvaez.loginnavigationapp.feature.auth.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return authRepository.login(email, password)
    }
}