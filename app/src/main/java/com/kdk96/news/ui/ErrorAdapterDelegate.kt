package com.kdk96.news.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import com.kdk96.common.ui.inflate
import com.kdk96.news.R
import kotlinx.android.synthetic.main.item_error.view.*

class ErrorAdapterDelegate(
    private val retryClickListener: () -> Unit
) : AdapterDelegate<MutableList<Any>>() {
    override fun isForViewType(items: MutableList<Any>, position: Int): Boolean =
        items[position] is ErrorItem

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(parent.inflate(R.layout.item_error))

    override fun onBindViewHolder(
        items: MutableList<Any>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {

    }

    private inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.retryButton.setOnClickListener { retryClickListener() }
        }
    }
}