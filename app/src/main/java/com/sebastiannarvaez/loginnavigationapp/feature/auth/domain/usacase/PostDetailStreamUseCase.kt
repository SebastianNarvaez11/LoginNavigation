package com.sebastiannarvaez.loginnavigationapp.feature.auth.domain.usacase

import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.models.PostDetailModel
import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostDetailStreamUseCase @Inject constructor(private val postRepository: PostRepository) {
    operator fun invoke(postId: String): Flow<PostDetailModel?> {
        return postRepository.postDetailStream(postId)
    }
}