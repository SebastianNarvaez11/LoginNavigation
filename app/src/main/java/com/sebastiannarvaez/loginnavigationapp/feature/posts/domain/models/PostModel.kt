package com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.models

data class PostModel(
    val id: String,
    val userId: String,
    val title: String,
    val content: String,
    val createdAt: String
)
