package com.kdk96.news.di

import android.arch.persistence.room.Room
import android.content.Context
import com.kdk96.news.data.db.NewsDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {
    @Provides
    @JvmStatic
    @Singleton
    fun provideDatabase(context: Context) = Room.databaseBuilder(
        context,
        NewsDb::class.java,
        "news_database.db"
    ).build()
}