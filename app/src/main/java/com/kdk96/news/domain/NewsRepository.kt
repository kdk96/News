package com.kdk96.news.domain

import io.reactivex.Single

interface NewsRepository {
    fun getNews(page: Int): Single<List<News>>
    fun getNewsFromCache(): Single<List<News>>
}