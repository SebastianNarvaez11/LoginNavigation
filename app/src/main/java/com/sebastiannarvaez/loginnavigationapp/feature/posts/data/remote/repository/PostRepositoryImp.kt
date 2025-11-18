package com.sebastiannarvaez.loginnavigationapp.feature.posts.data.remote.repository

import com.sebastiannarvaez.loginnavigationapp.feature.posts.data.remote.api.PostApiService
import com.sebastiannarvaez.loginnavigationapp.feature.posts.data.remote.response.toDomain
import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.models.PostModel
import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.repository.PostRepository
import com.sebastiannarvaez.todoappofflinefirst.data.utils.ErrorMapper
import javax.inject.Inject

class PostRepositoryImp @Inject constructor(
    private val postApi: PostApiService,
    private val errorMapper: ErrorMapper
) : PostRepository {
    override suspend fun getAllPost(): Result<List<PostModel>> {
        try {
            val result = postApi.getAllPost().map { it.toDomain() }
            return Result.success(result)
        } catch (e: Exception) {
            return Result.failure(errorMapper.map(e))
        }
    }
}