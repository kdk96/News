package com.kdk96.news.data.repository

import com.kdk96.common.di.Rx
import com.kdk96.news.data.db.NewsDb
import com.kdk96.news.data.db.NewsEntity
import com.kdk96.news.data.network.NewsApi
import com.kdk96.news.domain.News
import com.kdk96.news.domain.NewsRepository
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    @Rx.Io private val ioScheduler: Scheduler,
    private val newsDb: NewsDb
) : NewsRepository {
    override fun getNews(page: Int) = newsApi.getNews(page = page)
        .map { newsResponse -> newsResponse.articles }
        .map { articles ->
            articles.map {
                with(it) {
                    News(url, title, urlToImage, description, publishedAt)
                }
            }
        }.doOnSuccess { newsList ->
            val newsEntityList = newsList.map {
                with(it) {
                    NewsEntity(url, title, imageUrl, description, date)
                }
            }
            newsDb.runInTransaction {
                if (page == 1)
                    newsDb.newsDao().deleteAll()
                newsDb.newsDao().insertAll(newsEntityList)
            }
        }
        .subscribeOn(ioScheduler)


    override fun getNewsFromCache(): Single<List<News>> = newsDb.newsDao()
        .getNews()
        .map { newsEntityList -> newsEntityList.map { with(it) { News(url, title, imageUrl, description, date) } } }
        .subscribeOn(ioScheduler)
}