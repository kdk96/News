package com.kdk96.news.data.network

data class NewsResponse(
    val status: String,
    val articles: List<Article>
)

data class Article(
    val url: String,
    val title: String,
    val description: String,
    val urlToImage: String?,
    val publishedAt: String
)