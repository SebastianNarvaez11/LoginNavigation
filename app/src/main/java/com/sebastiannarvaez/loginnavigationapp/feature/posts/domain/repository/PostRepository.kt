package com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.repository

import com.sebastiannarvaez.loginnavigationapp.feature.posts.data.remote.response.PostDto
import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.models.PostModel

interface PostRepository {
    suspend fun getAllPost(): Result<List<PostModel>>
}