package com.kdk96.news.di

import com.kdk96.common.di.ComponentDependencies
import com.kdk96.common.di.DaggerComponent
import com.kdk96.common.di.PerFragment
import com.kdk96.common.di.Rx
import com.kdk96.news.data.db.NewsDb
import com.kdk96.news.data.network.NewsApi
import com.kdk96.news.data.repository.NewsRepositoryImpl
import com.kdk96.news.domain.NewsInteractor
import com.kdk96.news.domain.NewsInteractorImpl
import com.kdk96.news.domain.NewsRepository
import com.kdk96.news.presentation.NewsPresenter
import com.kdk96.news.ui.NewsFragment
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import retrofit2.Retrofit
import ru.terrakok.cicerone.Router

@PerFragment
@Component(modules = [NewsModule::class], dependencies = [NewsDependencies::class])
interface NewsComponent : DaggerComponent {
    fun inject(newsFragment: NewsFragment)
}

@Module
abstract class NewsModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        @PerFragment
        fun provideNewsApi(retrofit: Retrofit): NewsApi = retrofit.create(NewsApi::class.java)

        @Provides
        @JvmStatic
        @PerFragment
        fun provideNewsPresenter(
            router: Router,
            newsInteractor: NewsInteractor,
            @Rx.MainThread mainThreadScheduler: Scheduler
        ) = NewsPresenter(newsInteractor, router, mainThreadScheduler)
    }

    @Binds
    @PerFragment
    abstract fun provideNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository

    @Binds
    @PerFragment
    abstract fun provideNewsInteractor(newsInteractorImpl: NewsInteractorImpl): NewsInteractor

}

interface NewsDependencies : ComponentDependencies {
    @Rx.MainThread
    fun mainThreadScheduler(): Scheduler
    @Rx.Io
    fun ioScheduler(): Scheduler
    fun router(): Router
    fun retrofit(): Retrofit
    fun database(): NewsDb
}
