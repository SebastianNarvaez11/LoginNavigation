package com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.models

data class PostDetailModel(
    val id: String,
    val userId: String,
    val title: String,
    val content: String,
    val createdAt: String
)
