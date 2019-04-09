package com.kdk96.news.di

import com.kdk96.common.di.ComponentDependencies
import com.kdk96.common.di.DaggerComponent
import com.kdk96.news.ui.WebFragment
import dagger.Component
import ru.terrakok.cicerone.Router

@Component(dependencies = [WebDeps::class])
interface WebComponent : DaggerComponent {
    fun inject(webFragment: WebFragment)
}

interface WebDeps : ComponentDependencies {
    fun router(): Router
}