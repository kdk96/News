package com.kdk96.news.ui

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter
import com.kdk96.news.domain.News

class NewsAdapter(
    clickListener: (url: String) -> Unit,
    private val nextPageListener: () -> Unit,
    retryClickListener: () -> Unit
) : ListDelegationAdapter<MutableList<Any>>() {

    init {
        items = mutableListOf()
        delegatesManager.addDelegate(NewsAdapterDelegate(clickListener))
        delegatesManager.addDelegate(ErrorAdapterDelegate(retryClickListener))
    }

    fun setData(data: List<News>) {
        val oldItems = items.toList()

        items.clear()
        items.addAll(data)
        DiffUtil.calculateDiff(DiffCallback(items, oldItems), false)
            .dispatchUpdatesTo(this)
    }

    fun showNetworkError(isVisible: Boolean) {
        val oldData = items.toList()
        val currentError = isError()

        if (isVisible && !currentError) {
            items.add(ErrorItem())
            notifyItemInserted(items.lastIndex)
        } else if (!isVisible && currentError) {
            items.remove(items.last())
            notifyItemRemoved(oldData.lastIndex)
        }

    }

    private fun isError() = items.isNotEmpty() && items.last() is ErrorItem

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any?>) {
        super.onBindViewHolder(holder, position, payloads)
        if (position == items.size - 5) nextPageListener()
    }

    private inner class DiffCallback(
        private val newItems: List<Any>,
        private val oldItems: List<Any>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldItems.size
        override fun getNewListSize() = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]

            return if (newItem is News && oldItem is News) {
                newItem.url == oldItem.url
            } else {
                newItem is ErrorItem && oldItem is ErrorItem
            }
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]

            return if (newItem is News && oldItem is News) {
                newItem == oldItem
            } else {
                false
            }
        }
    }
}