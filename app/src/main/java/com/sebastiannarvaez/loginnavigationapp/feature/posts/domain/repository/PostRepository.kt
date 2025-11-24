package com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.repository

import androidx.paging.PagingData
import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.models.PostDetailModel
import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.models.SimplePostModel
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun getAllPost(): Result<List<SimplePostModel>>

    fun getPostPaging(): Flow<PagingData<SimplePostModel>>

    suspend fun getPostByIdAndCached(postId: String): Result<Unit>

    fun postDetailStream(postId: String): Flow<PostDetailModel?>
}