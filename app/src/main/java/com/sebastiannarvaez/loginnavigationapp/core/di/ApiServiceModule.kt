package com.sebastiannarvaez.loginnavigationapp.core.di

import com.sebastiannarvaez.loginnavigationapp.feature.auth.data.remote.api.AuthApiService
import com.sebastiannarvaez.loginnavigationapp.feature.posts.data.remote.api.PostApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {
    @Provides
    @Singleton
    fun provideAuthApiService(@PublicApi retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePostApiService(@PrivateApi retrofit: Retrofit): PostApiService {
        return retrofit.create(PostApiService::class.java)
    }
}