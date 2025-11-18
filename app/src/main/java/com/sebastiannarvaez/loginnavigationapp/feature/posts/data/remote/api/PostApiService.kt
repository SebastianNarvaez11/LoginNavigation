package com.sebastiannarvaez.loginnavigationapp.feature.posts.data.remote.api

import com.sebastiannarvaez.loginnavigationapp.feature.posts.data.remote.response.PostDto
import retrofit2.http.GET

interface PostApiService {
    @GET("/rest/v1/posts?select=*")
    suspend fun getAllPost(): List<PostDto>
}