package com.byjus.assignment.byjusAssignment.model

import java.io.Serializable

object NewsListt {

    data class NewsList(val status: String = "", val totalResults: Int = 0, val articles: List<Article> = listOf<Article>(
        Article()
    ))

    data class Article(
        var source: Source = Source(), var author: String = "", var title: String = "", var description: String= "",
        var url: String = "", var urlToImage: String = "", var publishedAt: String = "", var content: String = ""
    ) : Serializable

    data class Source(var id: String = " ", var name: String = " ")
}