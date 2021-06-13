package com.byjus.assignment.byjusAssignment.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
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
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val API_KEY = "37f0deb823454764bf560f80652c70c0"

@AndroidEntryPoint
class NewsFeedFragment(): Fragment() {

    private lateinit var viewBinding: FragmentNewsFeedBinding
    private var newsList: NewsListt.NewsList? = null
    private var newsDao: NewsArticlesDao? = null
    private var newsDisplayAdapter: NewsDisplayAdapter? = null
    private val newsFeedViewModel: NewsFeedViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentNewsFeedBinding.inflate(layoutInflater)
//        newsDao = NewsArticlesDatabase.getInstance(requireContext()).newsArticlesDao
//        val application = requireNotNull(activity).application
//
//        val viewModelProvider = NewsFeedViewModelFactory(newsDao!!, application)
//        newsFeedViewModel = ViewModelProviders.of(this, viewModelProvider).get(NewsFeedViewModel::class.java)
        viewBinding.newsFeedViewModel = newsFeedViewModel
        if (NewsFeedViewModel.networkConnectivity(requireContext()))
        newsFeedViewModel.getArticles("us","politics", API_KEY)
        viewBinding.lifecycleOwner = this
        initObservers(newsFeedViewModel)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.newsRecyclerView.layoutManager = LinearLayoutManager(activity)

//        newsList = NewsListt.NewsList("a",0,
//                mutableListOf((NewsListt.Article(NewsListt.Source(" a"," b"),"a","b","","c "," d","e "," f")),
//                        (NewsListt.Article(NewsListt.Source(" a"," b"),"a","b","","c "," d","e "," f"))))
        newsList = NewsListt.NewsList()
        newsDisplayAdapter = NewsDisplayAdapter(newsList!!, requireContext()){
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

    }



    @RequiresApi(Build.VERSION_CODES.M)
    private fun initObservers(newsFeedViewModel: NewsFeedViewModel) {
        lifecycleScope.launchWhenStarted {
            newsFeedViewModel.newsArticleLiveData.observe(viewLifecycleOwner, Observer {
                newsDisplayAdapter?.newsList = it
                newsDisplayAdapter?.notifyDataSetChanged()
            })

            if(!NewsFeedViewModel.networkConnectivity(requireContext()))
            newsFeedViewModel.convertedArticlesDb.observe(viewLifecycleOwner, Observer {

            })
        }
    }
}