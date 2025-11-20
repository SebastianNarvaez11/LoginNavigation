package com.sebastiannarvaez.loginnavigationapp.feature.posts.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.sebastiannarvaez.loginnavigationapp.core.presentation.navigation.Destinations
import com.sebastiannarvaez.loginnavigationapp.feature.auth.domain.usacase.PostDetailStreamUseCase
import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.models.PostDetailModel
import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.usecase.GetPostByIdAndCachedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPostByIdAndCachedUseCase: GetPostByIdAndCachedUseCase,
    private val postDetailStreamUseCase: PostDetailStreamUseCase
) : ViewModel() {

    private val params: Destinations.PostDetail = savedStateHandle.toRoute()
    private val postId = params.id

    private val _uiState = MutableStateFlow(PostDetailUiState())
    val uiState = _uiState.asStateFlow()

    val postDetailStream: StateFlow<PostDetailModel?> = postDetailStreamUseCase(postId)
        .catch { e -> _uiState.update { it.copy(error = e.message ?: "Error en stream de post") } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    init {
        fetchPost()
    }

    private fun fetchPost() {
        _uiState.update { it.copy(isFetchingPost = true) }

        viewModelScope.launch {
            delay(2000)

            getPostByIdAndCachedUseCase(postId)
                .onSuccess {
                    _uiState.update { it.copy(isFetchingPost = false) }
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(
                            error = e.message ?: "error al actualizar el post",
                            isFetchingPost = false
                        )
                    }
                }
        }
    }
}