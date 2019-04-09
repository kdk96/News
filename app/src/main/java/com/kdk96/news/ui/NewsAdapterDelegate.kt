package com.kdk96.news.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import com.kdk96.common.ui.inflate
import com.kdk96.news.GlideApp
import com.kdk96.news.R
import com.kdk96.news.domain.News
import kotlinx.android.synthetic.main.item_news.view.*
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class NewsAdapterDelegate(
    private val clickListener: (url: String) -> Unit

) : AdapterDelegate<MutableList<Any>>() {
    private val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)

    override fun isForViewType(items: MutableList<Any>, position: Int): Boolean =
        items[position] is News

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(parent.inflate(R.layout.item_news))

    override fun onBindViewHolder(
        items: MutableList<Any>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) = (holder as ViewHolder).bind(items[position] as News)

    private inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var news: News

        init {
            view.setOnClickListener { clickListener(news.url) }
        }

        fun bind(news: News) {
            this.news = news
            with(itemView) {
                titleTV.text = news.title
                val inst = Instant.parse(news.date)
                val localDateTime = LocalDateTime.ofInstant(inst, ZoneId.systemDefault())
                dateTV.text = formatter.format(localDateTime)
                descriptionTV.text = news.description
                GlideApp.with(itemView)
                    .load(news.imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .into(imageIV)
            }
        }
    }
}