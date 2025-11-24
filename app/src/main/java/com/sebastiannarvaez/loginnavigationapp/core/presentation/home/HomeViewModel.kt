package com.sebastiannarvaez.loginnavigationapp.core.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.usecase.GetAllPostUseCase
import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.usecase.GetPostsPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllPostUseCase: GetAllPostUseCase,
    private val getPostsPagingUseCase: GetPostsPagingUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    val postPagingStream = getPostsPagingUseCase().cachedIn(viewModelScope)

//    init {
//        getPost()
//    }
//
//    private fun getPost() {
//        _uiState.update { it.copy(isLoadingPost = true) }
//
//        viewModelScope.launch {
//            delay(2000)
//
//            getAllPostUseCase()
//                .onSuccess { posts ->
//                    _uiState.update {
//                        it.copy(
//                            posts = posts,
//                            isLoadingPost = false
//                        )
//                    }
//                }
//                .onFailure { e ->
//                    _uiState.update {
//                        it.copy(
//                            isLoadingPost = false,
//                            error = e.message ?: "Error al cargar post"
//                        )
//                    }
//                }
//        }
//    }
}