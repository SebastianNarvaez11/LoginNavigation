package com.sebastiannarvaez.loginnavigationapp.feature.auth.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sebastiannarvaez.loginnavigationapp.core.data.constants.DataStoreKeys
import com.sebastiannarvaez.loginnavigationapp.feature.auth.data.remote.api.AuthApiService
import com.sebastiannarvaez.loginnavigationapp.feature.auth.data.remote.request.LoginRequest
import com.sebastiannarvaez.loginnavigationapp.feature.auth.domain.repository.AuthRepository
import com.sebastiannarvaez.loginnavigationapp.feature.auth.presentation.AuthStatus
import com.sebastiannarvaez.todoappofflinefirst.data.utils.ErrorMapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject

class AuthRepositoryImp @Inject constructor(
    private val authApi: AuthApiService,
    private val dataStore: DataStore<Preferences>,
    private val json: Json,
    private val errorMapper: ErrorMapper
) : AuthRepository {

    companion object {
        private val ACCESS_TOKEN_KEY = DataStoreKeys.ACCESS_TOKEN_KEY
        private val REFRESH_TOKEN_KEY = DataStoreKeys.REFRESH_TOKEN_KEY
        private val USER_DATA_KEY = DataStoreKeys.USER_DATA_KEY
    }

    override fun statusStream(): Flow<AuthStatus> = dataStore.data
        .map { preferences -> preferences[ACCESS_TOKEN_KEY] }
        .distinctUntilChanged()
        .map { token ->
            if (token.isNullOrBlank()) {
                AuthStatus.Unauthenticated
            } else {
                AuthStatus.Authenticated
            }
        }
        .catch {
            emit(AuthStatus.Unauthenticated)
        }

    override suspend fun login(email: String, password: String): Result<Unit> {
        try {
            delay(2000)

            val response = authApi.login(LoginRequest(email, password))

            dataStore.edit { preferences ->
                preferences[ACCESS_TOKEN_KEY] = response.accessToken
                preferences[REFRESH_TOKEN_KEY] = response.refreshToken
                preferences[USER_DATA_KEY] = json.encodeToString(response.user)
            }

            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(errorMapper.map(e))
        }
    }

    override suspend fun logout(): Result<Unit> {
        try {
            dataStore.edit { it.clear() }
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(errorMapper.map(e))
        }
    }
}