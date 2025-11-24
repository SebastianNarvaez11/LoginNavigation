package com.sebastiannarvaez.loginnavigationapp.core.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.sebastiannarvaez.loginnavigationapp.feature.posts.presentation.components.PostItem

@Composable
fun HomeScreen(
    navigateToPostDetail: (id: String) -> Unit,
    logout: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
//    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val postPagingStream = viewModel.postPagingStream.collectAsLazyPagingItems()

    Column {
        Text(text = "Home screen")


        Button(onClick = { logout() }) {
            Text(text = "Cerrar sesion")
        }

//        AnimatedVisibility(visible = uiState.isLoadingPost) {
//            CircularProgressIndicator()
//        }
//
//        if (uiState.error != null) {
//            Text(text = "${uiState.error}")
//        }

        when {
            //carga inicial
            postPagingStream.loadState.refresh is LoadState.Loading && postPagingStream.itemCount == 0 -> {
                CircularProgressIndicator(color = Color.Green)
            }

            //sin datos
            postPagingStream.loadState.refresh is LoadState.NotLoading && postPagingStream.itemCount == 0 -> {
                Text("No hay datos para mostrar")
            }

            postPagingStream.loadState.hasError -> {
                val error = postPagingStream.loadState.refresh as LoadState.Error
                Text(text = error.error.localizedMessage ?: "Ocurrio un error")
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(
                count = postPagingStream.itemCount,
                key = postPagingStream.itemKey { it.id }) { index ->
                val post = postPagingStream[index]

                post?.let {
                    PostItem(post = post, onPress = { navigateToPostDetail(it.id) })
                }
            }

            //cargando mas datos
            when (postPagingStream.loadState.append) {
                is LoadState.Loading -> {
                    item { CircularProgressIndicator() }
                }

                is LoadState.Error -> {
                    item { Text("Error al cargar más", color = Color.Red) }
                }

                else -> {}
            }
        }

    }
}

