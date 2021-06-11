package com.byjus.assignment.byjus_assignment.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.byjus.assignment.byjus_assignment.MainActivity
import com.byjus.assignment.byjus_assignment.R
import com.byjus.assignment.byjus_assignment.adapter.NewsDisplayAdapter
import com.byjus.assignment.byjus_assignment.databinding.FragmentNewsFeedBinding
import com.byjus.assignment.byjus_assignment.model.NewsList
import com.byjus.assignment.byjus_assignment.rest.ApiClient
import com.byjus.assignment.byjus_assignment.rest.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

const val API_KEY = "37f0deb823454764bf560f80652c70c0"

class NewsFeedFragment(): Fragment() {

    private lateinit var viewBinding: FragmentNewsFeedBinding
    private var newsList: NewsList.NewsList? = null
    private lateinit var newsDisplayAdapter: NewsDisplayAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_news_feed, container, false)
        viewBinding = FragmentNewsFeedBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.newsRecyclerView.layoutManager = LinearLayoutManager(activity)

        newsList = NewsList.NewsList("a",0,
                mutableListOf((NewsList.Article(NewsList.Source(" a"," b"),"a","b","","c "," d","e "," f")),
                        (NewsList.Article(NewsList.Source(" a"," b"),"a","b","","c "," d","e "," f"))))
        newsDisplayAdapter = NewsDisplayAdapter(newsList!!, requireContext()) {
            val bundle = Bundle()
            bundle.putSerializable(NewsViewFragment.NEWS_LIST_ITEM, it)
            (activity as MainActivity).supportFragmentManager.commit {
                replace(
                    R.id.fragment_container,
                    NewsViewFragment.newInstance(bundle)
                ).addToBackStack("NEWSVIEWFRAGMENT")
            }
        }
        viewBinding.newsRecyclerView.adapter = newsDisplayAdapter

        getNewsList()
    }

    private fun getNewsList() {
        val call: Call<NewsList.NewsList> = ApiClient.getClient().create(ApiInterface::class.java)
                        .getLatestNews("us","politics", API_KEY)
        call.enqueue(object : Callback<NewsList.NewsList>{
            override fun onResponse(call: Call<NewsList.NewsList>, response: Response<NewsList.NewsList>) {
                newsList = response.body()
                newsDisplayAdapter.newsList = newsList!!
                newsDisplayAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<NewsList.NewsList>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}