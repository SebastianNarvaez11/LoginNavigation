package com.sebastiannarvaez.loginnavigationapp.feature.posts.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class) // Es necesario porque la API de PullToRefreshBox aún es experimental en algunas versiones
@Composable
fun PostDetailScreen(
    navigateBack: () -> Unit,
    viewModel: PostDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val post by viewModel.postDetailStream.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()

    PullToRefreshBox(
        isRefreshing = uiState.isRefetching,
        onRefresh = { viewModel.fetchPost() },
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Button(onClick = { navigateBack() }) {
                Text("Regresar")
            }

            if (uiState.isFetchingPost) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            if (uiState.error != null && !uiState.isFetchingPost && !uiState.isRefetching) {
                Text(text = uiState.error!!, color = Color.Red)

                Button(onClick = { viewModel.fetchPost() }) {
                    Text(text = "Reintentar")
                }
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
}