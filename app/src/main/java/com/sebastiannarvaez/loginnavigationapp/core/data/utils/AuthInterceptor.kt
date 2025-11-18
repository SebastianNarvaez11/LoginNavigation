package com.sebastiannarvaez.loginnavigationapp.core.data.utils

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.sebastiannarvaez.loginnavigationapp.core.data.constants.DataStoreKeys
import com.sebastiannarvaez.loginnavigationapp.core.data.remote.response.SupabaseErrorResponse
import com.sebastiannarvaez.loginnavigationapp.feature.auth.data.remote.api.AuthApiService
import com.sebastiannarvaez.loginnavigationapp.feature.auth.data.remote.request.RefreshTokenRequest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val authApi: AuthApiService,
    private val json: Json
) : Interceptor {
    companion object {
        private val ACCESS_TOKEN_KEY = DataStoreKeys.ACCESS_TOKEN_KEY
        private val REFRESH_TOKEN_KEY = DataStoreKeys.REFRESH_TOKEN_KEY
        private val USER_DATA_KEY = DataStoreKeys.USER_DATA_KEY

        private const val TAG = "AuthInterceptor"

        // Códigos específicos de Supabase
        private const val JWT_EXPIRED_CODE = "PGRST303"

        // Mensajes que indican token expirado
        private val TOKEN_EXPIRED_MESSAGES = setOf(
            "JWT expired",
            "jwt expired",
            "token expired"
        )
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val accessToken = runBlocking {
            dataStore.data.first()[ACCESS_TOKEN_KEY]
        }

        if (accessToken.isNullOrBlank()) {
            return chain.proceed(originalRequest)
        }

        val authenticatedRequest =
            originalRequest
                .newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()

        val response = chain.proceed(authenticatedRequest)

        // Si no es 401, dejar pasar el error
        if (response.code != 401) {
            return response
        }

        // Es 401, validar si es por token expirado
        val isTokenExpired = isTokenExpiredError(response)

        if (!isTokenExpired) {
            Log.w(TAG, "401 pero NO es token expirado, no se refresca")
            return response // Retornar el error original
        }

        // El token SÍ expiró, intentar refrescar
        Log.i(TAG, "Token expirado detectado, refrescando...")
        response.close() // Cerrar la respuesta anterior

        val newToken = runBlocking {
            refreshToken()
        }

        // Si no se pudo refrescar, retornar error
        if (newToken == null) {
            Log.e(TAG, "No se pudo refrescar el token")
            return chain.proceed(originalRequest) // Retornar request sin token
        }

        // Reintentar con el nuevo token
        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer $newToken")
            .build()

        return chain.proceed(newRequest)

    }

    private fun isTokenExpiredError(response: Response): Boolean {
        return try {
            val errorBody = response.peekBody(Long.MAX_VALUE).string()

            if (errorBody.isBlank()) {
                Log.w(TAG, "Error body vacío")
                return false
            }

            val errorResponse = json.decodeFromString<SupabaseErrorResponse>(errorBody)

            // Validar código específico de Supabase
            val isExpiredCode = errorResponse.code == JWT_EXPIRED_CODE

            // Validar mensaje
            val hasExpiredMessage =
                errorResponse.message?.lowercase() in TOKEN_EXPIRED_MESSAGES.map { it.lowercase() }

            val isExpired = isExpiredCode || hasExpiredMessage

            Log.d(
                TAG,
                "Error 401 analizado: code=${errorResponse.code}, message=${errorResponse.message}, isExpired=$isExpired"
            )

            isExpired
        } catch (e: Exception) {
            Log.e(TAG, "Error parseando respuesta 401", e)
            false // Si no se puede parsear, NO refrescar
        }
    }

    private suspend fun refreshToken(): String? {
        return try {
            val refreshToken = dataStore.data.first()[REFRESH_TOKEN_KEY]

            if (refreshToken.isNullOrBlank()) {
                Log.e(TAG, "No hay refresh token disponible")
                return null
            }

            Log.i(TAG, "Llamando a API de refresh token...")
            val response = authApi.refreshToken(RefreshTokenRequest(refreshToken = refreshToken))

            // Guardar nuevos tokens
            dataStore.edit { preferences ->
                preferences[ACCESS_TOKEN_KEY] = response.accessToken
                preferences[REFRESH_TOKEN_KEY] = response.refreshToken
                preferences[USER_DATA_KEY] = json.encodeToString(response.user)
            }

            Log.i(TAG, "Token refrescado exitosamente")
            response.accessToken
        } catch (e: Exception) {
            // Si el refresh token también expiró, limpiar sesión
            Log.w(TAG, "Refresh token inválido, limpiando sesión")
            dataStore.edit { it.clear() }
            null
        }
    }

}