package com.byjus.assignment.byjus_assignment.model

import java.io.Serializable

object NewsList {

    data class NewsList(val status: String, val totalResults: Int, val articles: List<Article>)

    data class Article(
        val source: Source, val author: String, val title: String, val description: String,
        val url: String, val urlToImage: String, val publishedAt: String, val content: String
    ) : Serializable

    data class Source(val id: String, val name: String)
}