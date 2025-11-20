package com.sebastiannarvaez.loginnavigationapp.feature.posts.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun PostDetailScreen(
    navigateBack: () -> Unit,
    viewModel: PostDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val post by viewModel.postDetailStream.collectAsStateWithLifecycle()

    //TODO: hacer funcion retry y pull to refresh

    Column {
        Button(onClick = { navigateBack() }) {
            Text("Regresar")
        }

        if (uiState.isFetchingPost) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary
            )
        }

        if (uiState.error != null) {
            Text(text = uiState.error!!, color = Color.Red)
        }

        if (post != null) {
            post?.let {
                Column {
                    Text(text = "Post id: ${it.id}")
                    Text(text = "Post title: ${it.title}")
                    Text(text = "Post content: ${it.content}")
                    Text(text = "Post date: ${it.createdAt}")
                }
            }
        }
    }
}