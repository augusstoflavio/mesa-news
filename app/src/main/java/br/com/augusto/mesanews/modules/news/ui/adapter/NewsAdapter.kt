package br.com.augusto.mesanews.modules.news.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import br.com.augusto.mesanews.R
import br.com.augusto.mesanews.app.helper.FotoHelper
import br.com.augusto.mesanews.app.helper.isVisible
import br.com.augusto.mesanews.modules.news.data.News

class NewsAdapter(
    val newsAdapterClickListener: NewsAdapterClickListener
): PagedListAdapter<News, NewsHolder>(NewsDiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.adapter_news, parent, false)
        return NewsHolder(v)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val news = getItem(position) ?: return


        holder.title.text = news.title
        holder.content.text = news.content

        holder.image.setOnClickListener {
            newsAdapterClickListener.onOpenClick(news)
        }

        holder.title.setOnClickListener {
            newsAdapterClickListener.onOpenClick(news)
        }

        holder.content.setOnClickListener {
            newsAdapterClickListener.onOpenClick(news)
        }

        if (news.imageUrl != null) {
            val fotoHelper = FotoHelper()
            fotoHelper.load(news.imageUrl, holder.image, holder.image.drawable.intrinsicWidth, 0)
            holder.image.isVisible(true)
        } else {
            holder.image.isVisible(false)
        }

        holder.sharedButton.setOnClickListener {
            newsAdapterClickListener.onSharedClick(news)
        }

        holder.favoriteButton.setOnClickListener {
            newsAdapterClickListener.onFavoriteClick(news)
        }

        if (news.favorite) {
            holder.favoriteButton.setImageResource(R.drawable.ic_heart_red)
        } else {
            holder.favoriteButton.setImageResource(R.drawable.ic_heart)
        }
    }

    fun changeFavState(news: News) {
        try {
            currentList?.indexOf(news).let {
                val position = it
                if (position != null) {
                    getItem(it)?.let {
                        it.favorite = news.favorite
                        notifyItemChanged(position)
                    }
                }
            }
        } catch (e: IndexOutOfBoundsException) {

        }

    }

    companion object {
        val NewsDiffCallback = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem == newItem
            }
        }
    }
}

interface NewsAdapterClickListener {
    fun onSharedClick(news: News)
    fun onFavoriteClick(news: News)
    fun onOpenClick(news: News)
}