package com.sebastiannarvaez.loginnavigationapp.core.presentation.home

import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.models.PostModel

data class HomeUiState(
    val posts: List<PostModel> = emptyList(),
    val isLoadingPost: Boolean = false,
    val error: String? = null
)
