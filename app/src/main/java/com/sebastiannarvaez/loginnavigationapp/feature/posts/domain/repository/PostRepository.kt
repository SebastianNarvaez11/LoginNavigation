package com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.repository

import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.models.PostDetailModel
import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.models.SimplePostModel
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun getAllPost(): Result<List<SimplePostModel>>

    suspend fun getPostByIdAndCached(postId: String): Result<Unit>

    fun postDetailStream(postId: String): Flow<PostDetailModel?>
}