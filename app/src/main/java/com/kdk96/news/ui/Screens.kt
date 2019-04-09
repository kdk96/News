package com.kdk96.news.ui

import ru.terrakok.cicerone.android.support.SupportAppScreen

interface Screens {
    object NewsScreen : SupportAppScreen() {
        override fun getFragment() = NewsFragment()
    }

    class WebScreen(val url: String) : SupportAppScreen() {
        override fun getFragment() = WebFragment.newIntance(url)
    }
}