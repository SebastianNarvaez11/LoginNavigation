package com.sebastiannarvaez.loginnavigationapp.feature.posts.presentation.post

import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.models.SimplePostModel

data class PostUiState(
    val posts: List<SimplePostModel> = emptyList(),
    val isLoadingPost: Boolean = false,
    val error: String? = null
)
