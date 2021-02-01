package com.moin.breakingnews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(private val listener : NewsClicked): RecyclerView.Adapter<NewsViewHolder>() {

    private val items : ArrayList<NewsData> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item , parent , false)
        val viewHolder = NewsViewHolder(view)
       view.setOnClickListener {
           listener.onItemClicked(items[viewHolder.adapterPosition])
       }
       return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val nowItem = items[position]
        holder.txtTitle.text=nowItem.newstitle
        holder.txtAuthor.text = nowItem.newsAuthor
        Glide.with(holder.itemView.context).load(nowItem.imageUrl).into(holder.imgNews)
    }

    override fun getItemCount(): Int {
        return items.size
    }
  fun newsUpdate(newsUpdate : ArrayList<NewsData>){
      items.clear()
      items.addAll(newsUpdate)

      notifyDataSetChanged()
  }
}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imgNews  : ImageView = itemView.findViewById(R.id.imgNews)
    val txtTitle :TextView = itemView.findViewById(R.id.txtTitle)
    val txtAuthor : TextView = itemView.findViewById(R.id.txtAuthor)


}

interface NewsClicked {
    fun onItemClicked(items : NewsData)
}