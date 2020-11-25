package br.com.augusto.mesanews.modules.news.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.augusto.mesanews.R
import br.com.augusto.mesanews.app.helper.FotoHelper
import br.com.augusto.mesanews.app.helper.isVisible
import br.com.augusto.mesanews.modules.news.data.News

class HighlightsAdapter: RecyclerView.Adapter<HighlightsAdapter.HighlightsHolder>() {

    private var itens: List<News> = listOf()

    fun update(itens: List<News>) {
        this.itens = itens
        notifyDataSetChanged()
    }

    class HighlightsHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.title)
        var image: ImageView = itemView.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HighlightsHolder {
        val v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.adapter_highlights, parent, false)
        return HighlightsHolder(v)
    }

    override fun onBindViewHolder(holder: HighlightsHolder, position: Int) {
        val news = itens.get(position)
        holder.title.text = news.title

        if (news.imageUrl != null) {
            val fotoHelper = FotoHelper()
            fotoHelper.load(news.imageUrl, holder.image, holder.image.drawable.intrinsicWidth, 0)
            holder.image.isVisible(true)
        } else {
            holder.image.isVisible(false)
        }
    }

    override fun getItemCount(): Int {
        return itens.size
    }
}