package com.sebastiannarvaez.loginnavigationapp.core.di

import android.content.Context
import androidx.room.Room
import com.sebastiannarvaez.loginnavigationapp.feature.posts.data.local.AppDatabase
import com.sebastiannarvaez.loginnavigationapp.feature.posts.data.local.dao.PostDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "LoginNavigationAppDatabase"
        ).build()
    }

    @Provides
    @Singleton
    fun providePostDao(db: AppDatabase): PostDao {
        return db.postDao()
    }
}