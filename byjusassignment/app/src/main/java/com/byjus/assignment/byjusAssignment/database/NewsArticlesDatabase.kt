package com.byjus.assignment.byjusAssignment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NewsArticle::class], version = 1, exportSchema = false)
abstract class NewsArticlesDatabase: RoomDatabase() {

    abstract val newsArticlesDao: NewsArticlesDao

    companion object {

        @Volatile
        private var INSTANCE: NewsArticlesDatabase? = null

        fun getInstance(context: Context) : NewsArticlesDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NewsArticlesDatabase::class.java,
                        "news_articles_database"
                    ). fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}