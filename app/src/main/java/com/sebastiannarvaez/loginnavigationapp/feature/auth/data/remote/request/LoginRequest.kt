package com.sebastiannarvaez.loginnavigationapp.feature.auth.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)
