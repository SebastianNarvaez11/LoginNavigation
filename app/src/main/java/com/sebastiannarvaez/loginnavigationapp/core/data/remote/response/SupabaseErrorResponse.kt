package com.sebastiannarvaez.loginnavigationapp.core.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SupabaseErrorResponse(
    @SerialName("message") val message: String? = null,
    @SerialName("hint") val hint: String? = null,
    @SerialName("code") val code: String? = null,
    @SerialName("details") val details: String? = null,
    @SerialName("msg") val msg: String? = null,
    @SerialName("error_code") val errorCode: String? = null
)
