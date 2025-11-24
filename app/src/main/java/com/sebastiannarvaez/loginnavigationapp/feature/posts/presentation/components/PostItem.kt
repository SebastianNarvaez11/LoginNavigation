package com.sebastiannarvaez.loginnavigationapp.feature.posts.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.models.SimplePostModel

@Composable
fun PostItem(
    modifier: Modifier = Modifier,
    post: SimplePostModel,
    onPress: (post: SimplePostModel) -> Unit,
) {
    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .height(250.dp)
            .background(MaterialTheme.colorScheme.inversePrimary)
            .clickable { onPress(post) }
            .padding(10.dp)
    ) {
        Text(text = post.title)
    }
}