package com.sebastiannarvaez.loginnavigationapp.feature.posts.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.models.PostModel

@Composable
fun PostItem(post: PostModel) {
    Column {
        Text(text = post.title)
        Text(text = post.content)
        Text(text = post.createdAt)
    }
}