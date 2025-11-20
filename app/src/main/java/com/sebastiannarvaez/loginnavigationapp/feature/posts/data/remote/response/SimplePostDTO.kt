package com.sebastiannarvaez.loginnavigationapp.feature.posts.data.remote.response

import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.models.SimplePostModel
import kotlinx.serialization.Serializable

@Serializable
data class SimplePostDTO(
    val id: String,
    val title: String,
)

fun SimplePostDTO.toDomain(): SimplePostModel {
    return SimplePostModel(
        id = id,
        title = title
    )
}