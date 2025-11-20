package com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.usecase

import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.repository.PostRepository
import javax.inject.Inject

class GetPostByIdAndCachedUseCase @Inject constructor(private val postRepository: PostRepository) {
    suspend operator fun invoke(postId: String): Result<Unit> {
        return postRepository.getPostByIdAndCached(postId)
    }
}