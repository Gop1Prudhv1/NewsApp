package com.byjus.assignment.byjusAssignment.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.byjus.assignment.byjus_assignment.R
import com.byjus.assignment.byjusAssignment.model.NewsListt
import com.byjus.assignment.byjusAssignment.utils.DateUtils


class NewsDisplayAdapter(var newsList: NewsListt.NewsList, context: Context, val onItemClicked: (NewsListt.Article) -> Unit):
        RecyclerView.Adapter<NewsDisplayAdapter.NewsHolder>(){

    private val mContext = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.news_item_layout, parent, false)
        return NewsHolder(view) {
            onItemClicked(newsList.articles[it])
        }
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val article = newsList.articles[position]
        holder.newsTitle.text = article.title
        holder.newsSource.text = article.source.name
        holder.publishedTime.text = DateUtils.getFormattedDateString(article.publishedAt)

        Glide.with(mContext).load(article.urlToImage).into(object : CustomTarget<Drawable>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                holder.cardLayout.background = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                holder.cardLayout.background = placeholder
            }
        })

    }

    override fun getItemCount(): Int {
        return if(newsList.articles!=null) newsList.articles.size else 0
    }

    inner class NewsHolder(view: View, onItemClicked: (Int) -> Unit): RecyclerView.ViewHolder(view) {
        internal var cardLayout = view.findViewById<ConstraintLayout>(R.id.card_layout)
        internal var newsTitle = view.findViewById<TextView>(R.id.news_title)
        internal var newsSource = view.findViewById<TextView>(R.id.source_name)
        internal var publishedTime = view.findViewById<TextView>(R.id.time_of_news)

        init {
            view.setOnClickListener { onItemClicked(adapterPosition) }
        }
    }

}