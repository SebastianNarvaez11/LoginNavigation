package com.sebastiannarvaez.loginnavigationapp.core.di

import com.sebastiannarvaez.loginnavigationapp.feature.auth.data.repository.AuthRepositoryImp
import com.sebastiannarvaez.loginnavigationapp.feature.auth.domain.repository.AuthRepository
import com.sebastiannarvaez.loginnavigationapp.feature.posts.data.repository.PostRepositoryImp
import com.sebastiannarvaez.loginnavigationapp.feature.posts.domain.repository.PostRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImp): AuthRepository

    @Binds
    @Singleton
    abstract fun bindPostRepository(impl: PostRepositoryImp): PostRepository
}