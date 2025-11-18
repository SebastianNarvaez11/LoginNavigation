package com.sebastiannarvaez.loginnavigationapp.core.di

import com.sebastiannarvaez.loginnavigationapp.BuildConfig
import com.sebastiannarvaez.loginnavigationapp.core.data.utils.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    @PublicApi
    fun providePublicOkHttpClient(): OkHttpClient { //para endpoints que no requieren autenticacion
        val apikey = BuildConfig.SUPABASE_API_KEY

        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("apikey", apikey)
                    .addHeader("Authorization", "Bearer $apikey")
                    .build()

                chain.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton
    @PrivateApi
    fun providePrivateOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient { //para endpoints que requieren autenticacion
        val apikey = BuildConfig.SUPABASE_API_KEY

        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("apikey", apikey)
                    .build()

                chain.proceed(request)
            }
            .addInterceptor(authInterceptor) // Token del usuario
            .build()
    }

    @Provides
    @Singleton
    fun provideJsonSerializer(): Json {
        return Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }

    @Provides
    @Singleton
    @PublicApi
    fun providePublicRetrofit(@PublicApi okHttpClient: OkHttpClient, json: Json): Retrofit {
        val baseUrl = BuildConfig.BASE_URL

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory("application/json; charset=UTF8".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    @PrivateApi
    fun providePrivateRetrofit(@PrivateApi okHttpClient: OkHttpClient, json: Json): Retrofit {
        val baseUrl = BuildConfig.BASE_URL

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory("application/json; charset=UTF8".toMediaType()))
            .build()
    }
}