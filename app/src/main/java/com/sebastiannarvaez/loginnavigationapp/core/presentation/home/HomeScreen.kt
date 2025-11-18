package com.sebastiannarvaez.loginnavigationapp.core.presentation.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.models.PostModel
import com.sebastiannarvaez.loginnavigationapp.feature.posts.presentation.components.PostItem

@Composable
fun HomeScreen(
    navigateToPostDetail: (id: String) -> Unit,
    logout: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        Log.i("HomeScreen", "se cargo la HomeScreen")
    }

    Column() {
        Text(text = "Home screen")

        Button(onClick = { viewModel.getPost() }) {
            if (uiState.isLoadingPost) {
                CircularProgressIndicator(color = Color.Red)
            } else {
                Text("Cargar post")
            }
        }

        Button(onClick = { logout() }) {
            Text(text = "Cerrar sesion")
        }

        if (uiState.error != null) {
            Text(text = "${uiState.error}")
        }

        LazyColumn() {
            items(uiState.posts, key = { it.id }) { post ->
                PostItem(post = post) { post -> navigateToPostDetail(post.id) }
            }
        }
    }
}

