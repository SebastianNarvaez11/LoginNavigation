package com.sebastiannarvaez.loginnavigationapp.feature.posts.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sebastiannarvaez.loginnavigationapp.feature.posts.data.local.dao.PostDao
import com.sebastiannarvaez.loginnavigationapp.feature.posts.data.local.entity.PostEntity

@Database(entities = [PostEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao
}