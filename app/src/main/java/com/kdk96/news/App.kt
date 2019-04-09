package com.kdk96.news

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.kdk96.common.di.ComponentDependenciesProvider
import com.kdk96.common.di.HasChildDependencies
import com.kdk96.news.di.AppComponent
import com.kdk96.news.di.DaggerAppComponent
import javax.inject.Inject

class App : Application(), HasChildDependencies {
    lateinit var appComponent: AppComponent
    @Inject
    override lateinit var dependencies: ComponentDependenciesProvider

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        appComponent = DaggerAppComponent.builder().context(this).build()
        appComponent.inject(this)
    }
}