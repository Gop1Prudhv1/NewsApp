package com.byjus.assignment.byjusAssignment.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NewsArticlesDao {

    @Insert
    fun insertArticle(article: NewsArticle)

    @Query("DELETE FROM news_articles_table")
    fun clearDataBase()

    @Query("SELECT * FROM news_articles_table ORDER BY articleId DESC")
    fun getAllArticles(): LiveData<List<NewsArticle>>

}