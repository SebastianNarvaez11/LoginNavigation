package com.sebastiannarvaez.loginnavigationapp.feature.auth.data.remote.api

import com.sebastiannarvaez.loginnavigationapp.feature.auth.data.remote.request.LoginRequest
import com.sebastiannarvaez.loginnavigationapp.feature.auth.data.remote.request.RefreshTokenRequest
import com.sebastiannarvaez.loginnavigationapp.feature.auth.data.remote.response.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApiService {
    @POST("/auth/v1/token?grant_type=password")
    suspend fun login(@Body credentials: LoginRequest): LoginResponseDto

    @POST("/auth/v1/token?grant_type=refresh_token")
    suspend fun refreshToken(@Body refresh: RefreshTokenRequest):LoginResponseDto
}