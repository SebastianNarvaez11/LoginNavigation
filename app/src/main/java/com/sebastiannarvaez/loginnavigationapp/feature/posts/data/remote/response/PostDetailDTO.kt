package com.sebastiannarvaez.loginnavigationapp.feature.posts.data.remote.response

import com.sebastiannarvaez.loginnavigationapp.feature.posts.data.local.entity.PostEntity
import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.models.PostDetailModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostDetailDTO(
    val id: String,
    @SerialName("user_id") val userId: String,
    val title: String,
    val content: String,
    @SerialName("created_at") val createdAt: String
)

fun PostDetailDTO.toDomain(): PostDetailModel {
    return PostDetailModel(
        id = id,
        userId = userId,
        title = title,
        content = content,
        createdAt = createdAt
    )
}

fun PostDetailDTO.toEntity(): PostEntity {
    return PostEntity(
        id = id,
        userId = userId,
        title = title,
        content = content,
        createdAt = createdAt
    )
}