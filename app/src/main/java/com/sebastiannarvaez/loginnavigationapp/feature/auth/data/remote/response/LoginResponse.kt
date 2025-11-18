package com.sebastiannarvaez.loginnavigationapp.feature.auth.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    @SerialName("access_token") val accessToken: String,
    @SerialName("refresh_token") val refreshToken: String,
    val user: LoginUserResponseDto
)

@Serializable
data class LoginUserResponseDto(
    val id: String,
    val email: String,
    val phone: String,
    @SerialName("last_sign_in_at") val lastSignInAt: String
)