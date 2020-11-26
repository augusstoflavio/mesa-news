package br.com.augusto.mesanews.modules.news.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.augusto.mesanews.R
import br.com.augusto.mesanews.app.helper.FotoHelper
import br.com.augusto.mesanews.app.helper.isVisible
import br.com.augusto.mesanews.modules.news.data.News

class FavoriteNewsAdapter(
    val onNewsAdapterClickListener: NewsAdapterClickListener
): RecyclerView.Adapter<NewsHolder>() {

    private var news: List<News> = listOf()

    fun update(itens: List<News>) {
        this.news = itens
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.adapter_news, parent, false)
        return NewsHolder(v)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val news = this.news[position]


        holder.title.text = news.title
        holder.content.text = news.content

        holder.image.setOnClickListener {
            onNewsAdapterClickListener.onOpenClick(news)
        }

        holder.title.setOnClickListener {
            onNewsAdapterClickListener.onOpenClick(news)
        }

        holder.content.setOnClickListener {
            onNewsAdapterClickListener.onOpenClick(news)
        }

        if (news.imageUrl != null) {
            val fotoHelper = FotoHelper()
            fotoHelper.load(news.imageUrl, holder.image, holder.image.drawable.intrinsicWidth, 0)
            holder.image.isVisible(true)
        } else {
            holder.image.isVisible(false)
        }

        holder.sharedButton.setOnClickListener {
            onNewsAdapterClickListener.onSharedClick(news)
        }

        holder.favoriteButton.setOnClickListener {
            onNewsAdapterClickListener.onFavoriteClick(news)
        }

        if (news.favorite) {
            holder.favoriteButton.setImageResource(R.drawable.ic_heart_red)
        } else {
            holder.favoriteButton.setImageResource(R.drawable.ic_heart)
        }
    }

    override fun getItemCount(): Int {
        return  this.news.size
    }
}