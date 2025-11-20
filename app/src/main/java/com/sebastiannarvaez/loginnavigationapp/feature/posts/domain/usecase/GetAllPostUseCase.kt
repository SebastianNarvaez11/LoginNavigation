package com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.usecase

import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.models.SimplePostModel
import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.repository.PostRepository
import javax.inject.Inject

class GetAllPostUseCase @Inject constructor(private val postRepository: PostRepository) {
    suspend operator fun invoke(): Result<List<SimplePostModel>> {
        return postRepository.getAllPost()
    }
}