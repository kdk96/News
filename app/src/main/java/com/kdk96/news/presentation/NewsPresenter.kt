package com.kdk96.news.presentation

import com.arellomobile.mvp.InjectViewState
import com.kdk96.common.presentation.BasePresenter
import com.kdk96.news.domain.News
import com.kdk96.news.domain.NewsInteractor
import com.kdk96.news.ui.Screens
import io.reactivex.Scheduler
import ru.terrakok.cicerone.Router
import java.io.IOException

@InjectViewState
class NewsPresenter(
    private val newsInteractor: NewsInteractor,
    private val router: Router,
    private val mainThreadScheduler: Scheduler
) : BasePresenter<NewsView>() {

    override fun onFirstViewAttach() {
        refreshNews()
    }

    private val paginator = Paginator(
        {
            newsInteractor.getNews(it)
                .observeOn(mainThreadScheduler)
        }, {
            newsInteractor.getNewsFromCache().observeOn(mainThreadScheduler)
        },
        object : Paginator.ViewController<News> {
            override fun showEmptyProgress(show: Boolean) {
                viewState.showEmptyProgress(show)
            }

            override fun showEmptyError(show: Boolean, error: Throwable?) {
                viewState.showEmptyError(show)
            }

            override fun showEmptyView(show: Boolean) {
            }

            override fun showData(show: Boolean, data: List<News>) {
                viewState.showError(false)
                viewState.showNews(show, data)
            }

            override fun showErrorMessage(error: Throwable) {
                if (error is IOException)
                    viewState.showError(true)
            }

            override fun showRefreshProgress(show: Boolean) {
                viewState.showRefreshProgress(show)
            }

            override fun showPageProgress(show: Boolean) {
            }
        }
    )


    fun onNewsClick(url: String) = router.navigateTo(Screens.WebScreen(url))

    fun refreshNews() = paginator.refresh()

    fun loadNextNewsPage() = paginator.loadNewPage()

    fun onBackPressed() = router.exit()

    override fun onDestroy() {
        super.onDestroy()
        paginator.release()
    }
}