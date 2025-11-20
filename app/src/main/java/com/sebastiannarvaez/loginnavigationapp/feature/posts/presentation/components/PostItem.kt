package com.sebastiannarvaez.loginnavigationapp.feature.posts.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.models.SimplePostModel

@Composable
fun PostItem(
    modifier: Modifier = Modifier,
    post: SimplePostModel,
    onPress: (post: SimplePostModel) -> Unit,
) {
    Column(modifier = modifier.clickable { onPress(post) }) {
        Text(text = post.title)
    }
}