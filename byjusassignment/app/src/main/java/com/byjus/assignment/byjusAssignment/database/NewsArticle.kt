package com.byjus.assignment.byjusAssignment.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.byjus.assignment.byjusAssignment.model.NewsListt
import com.google.gson.Gson
import com.google.gson.JsonArray

@Entity(tableName = "news_articles_table")
data class NewsArticle(

    @PrimaryKey(autoGenerate = true)
    var articleId: Long = 0L,
    var source: String = Gson().toJson(NewsListt.Source()),
    var author: String = "",
    var title: String = "",
    var description: String = "",
    var url: String = "",
    var urlToImage: String = "",
    var publishedAt: String = "",
    var content: String = ""

)