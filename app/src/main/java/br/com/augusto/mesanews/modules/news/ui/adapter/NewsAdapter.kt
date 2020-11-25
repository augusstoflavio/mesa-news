package br.com.augusto.mesanews.modules.news.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import br.com.augusto.mesanews.R
import br.com.augusto.mesanews.app.helper.FotoHelper
import br.com.augusto.mesanews.modules.news.data.News

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.NewsHolder>() {

    private var news: List<News> = listOf()

    fun update(itens: List<News>) {
        this.news = itens
        notifyDataSetChanged()
    }

    class NewsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.title)
        var content: TextView = itemView.findViewById(R.id.content)
        var image: ImageView = itemView.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.adapter_news, parent, false)
        return NewsHolder(v)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val news1 = news.get(position)
        holder.title.text = news1.title
        holder.content.text = news1.content

        if (news1.imageUrl != null) {
            val fotoHelper = FotoHelper()
            fotoHelper.load(news1.imageUrl, holder.image, holder.image.drawable.intrinsicWidth, 0)
            holder.image.isVisible = true
        } else {
            holder.image.isVisible = false
        }
    }

    override fun getItemCount(): Int {
        return news.size
    }
}