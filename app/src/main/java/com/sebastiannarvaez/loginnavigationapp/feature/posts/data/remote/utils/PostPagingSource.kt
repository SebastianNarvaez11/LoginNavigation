package com.sebastiannarvaez.loginnavigationapp.feature.posts.data.remote.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sebastiannarvaez.loginnavigationapp.feature.posts.data.remote.api.PostApiService
import com.sebastiannarvaez.loginnavigationapp.feature.posts.data.remote.response.toDomain
import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.models.SimplePostModel

class PostPagingSource(private val postApi: PostApiService) : PagingSource<Int, SimplePostModel>() {
    override fun getRefreshKey(state: PagingState<Int, SimplePostModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SimplePostModel> {
        // 1. ¿En qué pagina estamos? Si es null, empezamos por la 0 (offset 0)
        val currentPage = params.key ?: 0

        return try {
            // 2. Calcular offset para Supabase
            // Si params.loadSize es 10 y pageIndex es 1 -> offset = 10
            val offset = currentPage * params.loadSize

            // 3. Llamada a la API
            val response = postApi.getPostPaging(limit = params.loadSize, offset = offset)

            val posts = response.map { it.toDomain() }

            // 4. Definir cuál es la siguiente página
            // Si la respuesta viene vacía o con menos items de lo esperado, llegamos al final
            val nextPage =
                if (response.isEmpty()) null else currentPage + 1

            //5. pagina anterior
            val prevPage = if (currentPage == 0) null else currentPage - 1

            LoadResult.Page(posts, prevPage, nextPage)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}