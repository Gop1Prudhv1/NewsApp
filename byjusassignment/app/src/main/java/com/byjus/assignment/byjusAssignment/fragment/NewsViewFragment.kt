package com.byjus.assignment.byjusAssignment.fragment

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.byjus.assignment.byjus_assignment.R
import com.byjus.assignment.byjusAssignment.model.NewsListt
import com.byjus.assignment.byjusAssignment.utils.DateUtils

class NewsViewFragment (): Fragment() {

    companion object {
        const val NEWS_LIST_ITEM = "NEWS_LIST_ITEM"

        fun newInstance(bundle: Bundle): NewsViewFragment {
            val fragment = NewsViewFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var newsTitle:TextView
    private lateinit var newsDesc: TextView
    private lateinit var publishedAt: TextView
    private lateinit var source: TextView
    private lateinit var newsViewLayout: ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.news_view_fragment, container, false)
        newsTitle = view.findViewById(R.id.news_view_title)
        newsDesc = view.findViewById(R.id.news_view_description)
        publishedAt = view.findViewById(R.id.news_view_published_At)
        source = view.findViewById(R.id.news_view_source)
        newsViewLayout = view.findViewById(R.id.news_view_layout)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val article = (arguments?.getSerializable(NEWS_LIST_ITEM) as NewsListt.Article)
        newsTitle.text = article.title
        newsDesc.text = article.description
        publishedAt.text = DateUtils.getFormattedDateString(article.publishedAt)
        source.text = article.source.name

        Glide.with(requireContext()).load(article.urlToImage).into(object : CustomTarget<Drawable>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                newsViewLayout.background = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                newsViewLayout.background = placeholder
            }
        })
    }
}