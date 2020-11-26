package br.com.augusto.mesanews.modules.news.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import br.com.augusto.mesanews.R
import br.com.augusto.mesanews.app.helper.FotoHelper
import br.com.augusto.mesanews.app.helper.isVisible
import br.com.augusto.mesanews.app.ui.adapter.OnClickItemAdapterListener
import br.com.augusto.mesanews.modules.news.data.News

class NewsAdapter(
    val onClickShared: OnClickItemAdapterListener<News>,
    val onClickView: OnClickItemAdapterListener<News>,
    val onClickFavorite: OnClickItemAdapterListener<News>
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
            onClickView.clickItem(news)
        }

        holder.title.setOnClickListener {
            onClickView.clickItem(news)
        }

        holder.content.setOnClickListener {
            onClickView.clickItem(news)
        }

        if (news.imageUrl != null) {
            val fotoHelper = FotoHelper()
            fotoHelper.load(news.imageUrl, holder.image, holder.image.drawable.intrinsicWidth, 0)
            holder.image.isVisible(true)
        } else {
            holder.image.isVisible(false)
        }

        holder.sharedButton.setOnClickListener {
            onClickShared.clickItem(news)
        }

        holder.favoriteButton.setOnClickListener {
            onClickFavorite.clickItem(news)
        }

        if (news.favorite) {
            holder.favoriteButton.setImageResource(R.drawable.ic_heart_red)
        } else {
            holder.favoriteButton.setImageResource(R.drawable.ic_heart)
        }
    }

    fun changeFavState(news: News) {
        currentList?.indexOf(news).let {
            val position = it
            if (position != null) {
                getItem(it)?.let {
                    it.favorite = news.favorite
                    notifyItemChanged(position)
                }
            }
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