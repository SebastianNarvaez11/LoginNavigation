package com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.usecase

import androidx.paging.PagingData
import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.models.SimplePostModel
import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostsPagingUseCase @Inject constructor(private val postRepository: PostRepository) {
    operator fun invoke(): Flow<PagingData<SimplePostModel>> {
        return postRepository.getPostPaging()
    }
}