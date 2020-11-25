package br.com.augusto.mesanews.modules.news.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.augusto.mesanews.R
import br.com.augusto.mesanews.app.helper.FotoHelper
import br.com.augusto.mesanews.app.helper.isVisible
import br.com.augusto.mesanews.app.ui.adapter.OnClickItemAdapterListener
import br.com.augusto.mesanews.modules.news.data.News

class NewsAdapter(
    val onClickShared: OnClickItemAdapterListener<News>
): PagedListAdapter<News, NewsAdapter.NewsHolder>(NewsDiffCallback) {

    class NewsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.title)
        var content: TextView = itemView.findViewById(R.id.content)
        var image: ImageView = itemView.findViewById(R.id.image)
        var sharedButton: ImageView = itemView.findViewById(R.id.shared_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.adapter_news, parent, false)
        return NewsHolder(v)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val news1 = getItem(position) ?: return

        holder.title.text = news1.title
        holder.content.text = news1.content

        if (news1.imageUrl != null) {
            val fotoHelper = FotoHelper()
            fotoHelper.load(news1.imageUrl, holder.image, holder.image.drawable.intrinsicWidth, 0)
            holder.image.isVisible(true)
        } else {
            holder.image.isVisible(false)
        }

        holder.sharedButton.setOnClickListener {
            onClickShared.clickItem(news1)
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