package com.sebastiannarvaez.loginnavigationapp.feature.posts.presentation.detail

data class PostDetailUiState(
    val isFetchingPost: Boolean = false, //cargando desde api
    val error: String? = null
)
