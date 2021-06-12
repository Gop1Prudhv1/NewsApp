package com.byjus.assignment.byjusAssignment.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val API_KEY = "37f0deb823454764bf560f80652c70c0"

class NewsFeedFragment(): Fragment() {

    private lateinit var viewBinding: FragmentNewsFeedBinding
    private var newsList: NewsListt.NewsList? = null
    private var newsDao: NewsArticlesDao? = null
    private var newsDisplayAdapter: NewsDisplayAdapter? = null
    private lateinit var newsFeedViewModel: NewsFeedViewModel
//    private lateinit var newsFVmodel: NewsFeedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentNewsFeedBinding.inflate(layoutInflater)
        newsDao = NewsArticlesDatabase.getInstance(requireContext()).newsArticlesDao
        val application = requireNotNull(activity).application
//        val newsFeedViewModel: NewsFeedViewModel by viewModels{ NewsFeedViewModelFactory(newsDao!!, requireNotNull(activity).application)}
        //viewBinding.newsFeedViewModel = newsFeedViewModel
//        newsFVmodel = newsFeedViewModel

//        viewBinding.lifecycleOwner = this
//        val newsFeedViewModel: NewsFeedViewModel by viewModels()
//        newsFeedViewModel.getArticles("us","politics", API_KEY)
        //initObservers(newsFeedViewModel)
//        newsFVmodel = newsFeedViewModel

        val viewModelProvider = NewsFeedViewModelFactory(newsDao!!, application)
        newsFeedViewModel = ViewModelProviders.of(this, viewModelProvider).get(NewsFeedViewModel::class.java)
        viewBinding.newsFeedViewModel = newsFeedViewModel
        viewBinding.lifecycleOwner = this
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.newsRecyclerView.layoutManager = LinearLayoutManager(activity)

//        newsList = NewsList.NewsList("a",0,
//                mutableListOf((NewsList.Article(NewsList.Source(" a"," b"),"a","b","","c "," d","e "," f")),
//                        (NewsList.Article(NewsList.Source(" a"," b"),"a","b","","c "," d","e "," f"))))
        //newsFeedViewModel.getArticles("us","politics", API_KEY)

//        newsFVmodel.getArticles("us","politics", API_KEY)
//        val newsFeedViewModel: NewsFeedViewModel by viewModels()
//        newsFeedViewModel.getArticles("us","politics", API_KEY)
        getNewsList()
        //initObservers(newsFeedViewModel)
    }

    private fun getNewsList() {
        val call: Call<NewsListt.NewsList> = ApiClient.getClient().create(ApiInterface::class.java)
                        .getLatestNews("us","politics", API_KEY)
        call.enqueue(object : Callback<NewsListt.NewsList>{
            override fun onResponse(call: Call<NewsListt.NewsList>, response: Response<NewsListt.NewsList>) {
                newsList = response.body()
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
            }

            override fun onFailure(call: Call<NewsListt.NewsList>, t: Throwable) {

            }

        })
    }
    private fun initObservers(newsFeedViewModel: NewsFeedViewModel) {
        lifecycleScope.launchWhenStarted {
            newsFeedViewModel._newsArticleLiveData.observe(viewLifecycleOwner, Observer {
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
}