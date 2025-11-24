package com.sebastiannarvaez.loginnavigationapp.feature.posts.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sebastiannarvaez.loginnavigationapp.core.domain.models.DomainError
import com.sebastiannarvaez.loginnavigationapp.feature.posts.data.local.dao.PostDao
import com.sebastiannarvaez.loginnavigationapp.feature.posts.data.local.entity.toDomain
import com.sebastiannarvaez.loginnavigationapp.feature.posts.data.remote.api.PostApiService
import com.sebastiannarvaez.loginnavigationapp.feature.posts.data.remote.response.PostDetailDTO
import com.sebastiannarvaez.loginnavigationapp.feature.posts.data.remote.response.toDomain
import com.sebastiannarvaez.loginnavigationapp.feature.posts.data.remote.response.toEntity
import com.sebastiannarvaez.loginnavigationapp.feature.posts.data.remote.utils.PostPagingSource
import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.models.PostDetailModel
import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.models.SimplePostModel
import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.repository.PostRepository
import com.sebastiannarvaez.todoappofflinefirst.data.utils.ErrorMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PostRepositoryImp @Inject constructor(
    private val postApi: PostApiService,
    private val postDao: PostDao,
    private val errorMapper: ErrorMapper
) : PostRepository {
    override suspend fun getAllPost(): Result<List<SimplePostModel>> {
        try {
            val result = postApi.getAllPost().map { it.toDomain() }
            return Result.success(result)
        } catch (e: Exception) {
            return Result.failure(errorMapper.map(e))
        }
    }

    override fun getPostPaging(): Flow<PagingData<SimplePostModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5, // Cuantos items traer por petición
                prefetchDistance = 1,// Cargar más cuando falten 2 items para el final
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PostPagingSource(postApi) }
        ).flow
    }

    override suspend fun getPostByIdAndCached(postId: String): Result<Unit> {
        try {
            //buscar el post en la api
            val result: List<PostDetailDTO> = postApi.getPostById(id = "eq.$postId")

            //si se encontro el post devolver error
            if (result.isEmpty()) {
                return Result.failure(DomainError.ApiError("No pudimos encontrar este post :("))
            }

            val post = result[0].toEntity()

            //insertar en room
            postDao.insertPost(post) //esto actualiza la db y se ejecuta el flow postDetailStream automaticamente

            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(errorMapper.map(e))
        }
    }

    override fun postDetailStream(postId: String): Flow<PostDetailModel?> {
        return postDao.getPostById(postId)
            .map { it?.toDomain() } //devuelve null si no logra hacer el .toDomain
    }
}