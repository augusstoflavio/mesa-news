package br.com.augusto.mesanews.modules.news.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.augusto.mesanews.R

class NewsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var title: TextView = itemView.findViewById(R.id.title)
    var content: TextView = itemView.findViewById(R.id.content)
    var image: ImageView = itemView.findViewById(R.id.image)
    var sharedButton: ImageView = itemView.findViewById(R.id.shared_button)
    var favoriteButton: ImageView = itemView.findViewById(R.id.favorite_button)
}