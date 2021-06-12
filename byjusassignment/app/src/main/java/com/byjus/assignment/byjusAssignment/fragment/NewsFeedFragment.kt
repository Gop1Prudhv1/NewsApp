package com.byjus.assignment.byjusAssignment.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.byjus.assignment.byjusAssignment.MainActivity
import com.byjus.assignment.byjus_assignment.R
import com.byjus.assignment.byjusAssignment.adapter.NewsDisplayAdapter
import com.byjus.assignment.byjusAssignment.database.NewsArticlesDao
import com.byjus.assignment.byjusAssignment.database.NewsArticlesDatabase
import com.byjus.assignment.byjus_assignment.databinding.FragmentNewsFeedBinding
import com.byjus.assignment.byjusAssignment.model.NewsListt
import com.byjus.assignment.byjusAssignment.rest.ApiClient
import com.byjus.assignment.byjusAssignment.rest.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val API_KEY = "37f0deb823454764bf560f80652c70c0"

class NewsFeedFragment(): Fragment() {

    private lateinit var viewBinding: FragmentNewsFeedBinding
    private var newsList: NewsListt.NewsList? = null
    private var newsDao: NewsArticlesDao? = null
    private var newsDisplayAdapter: NewsDisplayAdapter? = null
    val newsFeedViewModel: NewsFeedViewModel by viewModels()
    private lateinit var newsFVmodel: NewsFeedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentNewsFeedBinding.inflate(layoutInflater)
        newsDao = NewsArticlesDatabase.getInstance(requireContext()).newsArticlesDao
//        val newsFeedViewModel: NewsFeedViewModel by viewModels{ NewsFeedViewModelFactory(newsDao!!, requireNotNull(activity).application)}
        //viewBinding.newsFeedViewModel = newsFeedViewModel
        newsFVmodel = newsFeedViewModel

//        viewBinding.lifecycleOwner = this
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.newsRecyclerView.layoutManager = LinearLayoutManager(activity)

//        newsList = NewsList.NewsList("a",0,
//                mutableListOf((NewsList.Article(NewsList.Source(" a"," b"),"a","b","","c "," d","e "," f")),
//                        (NewsList.Article(NewsList.Source(" a"," b"),"a","b","","c "," d","e "," f"))))
        //newsFeedViewModel.getArticles("us","politics", API_KEY)
        //getNewsList()
        newsFVmodel.getArticles("us","politics", API_KEY)
        initObservers()
    }

    private fun getNewsList() {
//        val call: Call<NewsListt.NewsList> = ApiClient.getClient().create(ApiInterface::class.java)
//                        .getLatestNews("us","politics", API_KEY)
//        call.enqueue(object : Callback<NewsListt.NewsList>{
//            override fun onResponse(call: Call<NewsListt.NewsList>, response: Response<NewsListt.NewsList>) {
//                newsList = response.body()
//                //newsDisplayAdapter?.newsList = newsList!!
////                newsDisplayAdapter.notifyDataSetChanged()
//                newsDisplayAdapter = NewsDisplayAdapter(newsList!!, requireContext()) {
//                    val bundle = Bundle()
//                    bundle.putSerializable(NewsViewFragment.NEWS_LIST_ITEM, it)
//                    (activity as MainActivity).supportFragmentManager.commit {
//                        replace(
//                            R.id.fragment_container,
//                            NewsViewFragment.newInstance(bundle)
//                        ).addToBackStack("NEWSVIEWFRAGMENT")
//                    }
//                }
//                viewBinding.newsRecyclerView.adapter = newsDisplayAdapter
//            }
//
//            override fun onFailure(call: Call<NewsListt.NewsList>, t: Throwable) {
//
//            }
//
//        })
    }
    private fun initObservers() {
        newsFeedViewModel.newsArticleLiveData.observe(viewLifecycleOwner, Observer {
            newsDisplayAdapter = NewsDisplayAdapter(it, requireContext()) {
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
        })
    }
}