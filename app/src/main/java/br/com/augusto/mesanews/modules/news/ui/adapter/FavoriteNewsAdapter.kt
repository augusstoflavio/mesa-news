package br.com.augusto.mesanews.modules.news.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.augusto.mesanews.R
import br.com.augusto.mesanews.app.helper.FotoHelper
import br.com.augusto.mesanews.app.helper.isVisible
import br.com.augusto.mesanews.app.ui.adapter.OnClickItemAdapterListener
import br.com.augusto.mesanews.modules.news.data.News

class FavoriteNewsAdapter(
    val onClickShared: OnClickItemAdapterListener<News>,
    val onClickView: OnClickItemAdapterListener<News>,
    val onClickFavorite: OnClickItemAdapterListener<News>
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

    override fun getItemCount(): Int {
        return  this.news.size
    }
}