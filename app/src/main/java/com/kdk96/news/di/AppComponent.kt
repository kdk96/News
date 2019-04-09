package com.kdk96.news.di

import android.content.Context
import com.kdk96.common.di.ComponentDependencies
import com.kdk96.common.di.ComponentDependenciesKey
import com.kdk96.common.di.Rx
import com.kdk96.news.App
import com.kdk96.news.ui.MainActivity
import dagger.*
import dagger.multibindings.IntoMap
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ComponentDependenciesModule::class,
        NavigationModule::class,
        SchedulersModule::class,
        NewsApiModule::class,
        DatabaseModule::class
    ]
)
interface AppComponent : NewsDependencies, WebDeps {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
    fun inject(mainActivity: MainActivity)
}


@Module
object NavigationModule {
    @Provides
    @JvmStatic
    @Singleton
    fun provideCicerone(): Cicerone<Router> = Cicerone.create()

    @Provides
    @JvmStatic
    @Singleton
    fun provideRouter(cicerone: Cicerone<Router>): Router = cicerone.router

    @Provides
    @JvmStatic
    @Singleton
    fun provideNavigatorHolder(cicerone: Cicerone<Router>): NavigatorHolder = cicerone.navigatorHolder

}

@Module
object SchedulersModule {
    @Provides
    @JvmStatic
    @Singleton
    @Rx.MainThread
    fun provideMainThreadScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @JvmStatic
    @Singleton
    @Rx.Io
    fun provideIoScheduler() = Schedulers.io()
}

@Module
interface ComponentDependenciesModule {
    @Binds
    @IntoMap
    @ComponentDependenciesKey(NewsDependencies::class)
    fun provideNewsDependencies(appComponent: AppComponent): ComponentDependencies

    @Binds
    @IntoMap
    @ComponentDependenciesKey(WebDeps::class)
    fun provideWebDependencies(appComponent: AppComponent): ComponentDependencies
}
