package com.sebastiannarvaez.loginnavigationapp.feature.posts.data.remote.response

import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.models.PostModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    val id: String,
    @SerialName("user_id") val userId: String,
    val title: String,
    val content: String,
    @SerialName("created_at") val createdAt: String
)

fun PostDto.toDomain(): PostModel {
    return PostModel(
        id = id,
        userId = userId,
        title = title,
        content = content,
        createdAt = createdAt
    )
}