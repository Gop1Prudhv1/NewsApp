package com.byjus.assignment.byjusAssignment

import android.app.Application
import android.content.Context
import com.byjus.assignment.byjusAssignment.database.NewsArticlesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsAppModule {

    @Provides
    @Singleton
    fun provideNewsDataBase(@ApplicationContext context: Context): NewsArticlesDatabase {
        return NewsArticlesDatabase.getInstance(context = context)
    }

}