package com.sebastiannarvaez.loginnavigationapp.feature.posts.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.models.PostDetailModel

//se usa esta entidad para guardar el cache de la llamada a postById
@Entity(tableName = "posts", indices = [])
data class PostEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val title: String,
    val content: String,
    val createdAt: String
)

fun PostEntity.toDomain(): PostDetailModel {
    return PostDetailModel(
        id = id,
        userId = userId,
        title = title,
        content = content,
        createdAt = createdAt
    )
}

