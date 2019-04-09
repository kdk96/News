package com.kdk96.news.ui


import android.os.Bundle
import com.kdk96.common.di.findComponentDependencies
import com.kdk96.common.ui.BaseFragment
import com.kdk96.news.di.DaggerWebComponent
import kotlinx.android.synthetic.main.fragment_web.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject


class WebFragment : BaseFragment() {
    companion object {
        private const val ARG_URL = "url argument"
        fun newIntance(url: String) = WebFragment().apply {
            arguments = Bundle().apply { putString(ARG_URL, url) }
        }
    }

    override val layoutRes = com.kdk96.news.R.layout.fragment_web
    @Inject
    lateinit var router: Router

    init {
        componentBuilder = {
            DaggerWebComponent.builder().webDeps(findComponentDependencies()).build()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        getComponent<DaggerWebComponent>().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        webView.loadUrl(arguments!!.getString(ARG_URL))
    }

    override fun onBackPressed() {
        router.exit()
    }
}
