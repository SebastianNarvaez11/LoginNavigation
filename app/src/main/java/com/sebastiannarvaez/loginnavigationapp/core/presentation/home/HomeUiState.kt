package com.sebastiannarvaez.loginnavigationapp.core.presentation.home

import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.models.SimplePostModel

data class HomeUiState(
    val posts: List<SimplePostModel> = emptyList(),
    val isLoadingPost: Boolean = false,
    val error: String? = null
)
