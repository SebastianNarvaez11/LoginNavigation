package com.sebastiannarvaez.loginnavigationapp.feature.posts.data.remote.api

import com.sebastiannarvaez.loginnavigationapp.feature.posts.data.remote.response.SimplePostDTO
import com.sebastiannarvaez.loginnavigationapp.feature.posts.data.remote.response.PostDetailDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface PostApiService {
    @GET("/rest/v1/posts") //rest/v1/posts?select=*"
    suspend fun getAllPost(
        @Query(value = "select", encoded = true) select: String = "id,title"
    ): List<SimplePostDTO>

    @GET("/rest/v1/posts") ///rest/v1/posts?id=eq.1&select=*"
    suspend fun getPostById(
        @Query(value = "id", encoded = true) id: String,
        @Query(value = "select", encoded = true) select: String = "*"
    ): List<PostDetailDTO>
}