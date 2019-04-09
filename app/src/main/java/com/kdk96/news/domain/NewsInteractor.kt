package com.kdk96.news.domain

import io.reactivex.Single
import javax.inject.Inject

interface NewsInteractor {
    fun getNews(page: Int): Single<List<News>>
    fun getNewsFromCache(): Single<List<News>>
}

class NewsInteractorImpl @Inject constructor(
    private val repository: NewsRepository
) : NewsInteractor {
    override fun getNews(page: Int) = repository.getNews(page)

    override fun getNewsFromCache() = repository.getNewsFromCache()
}