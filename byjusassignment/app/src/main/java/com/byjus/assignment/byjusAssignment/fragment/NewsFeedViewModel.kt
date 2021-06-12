package com.byjus.assignment.byjusAssignment.fragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.byjus.assignment.byjusAssignment.database.NewsArticlesDao
import com.byjus.assignment.byjusAssignment.model.NewsListt
import com.byjus.assignment.byjusAssignment.rest.ApiClient
import com.byjus.assignment.byjusAssignment.rest.ApiInterface
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class NewsFeedViewModel(/*val database: NewsArticlesDao,
                        application: Application*/)
    :ViewModel() {

    private var _newsArticleLiveData = MutableLiveData<NewsListt.NewsList>()
    val newsArticleLiveData: LiveData<NewsListt.NewsList> get() = _newsArticleLiveData

//    init {
//        getArticles("us", "politics", API_KEY)
//    }
        fun getArticles(country: String, category: String, apiKey: String) {
            viewModelScope.launch {
                Log.d("API FETCH", "API FETCH SUCCESSFUL")
                withContext(Dispatchers.IO) {


                    val response = ApiClient.getClient().create(ApiInterface::class.java)
                        .getLatestNews(country, category, apiKey).awaitResponse()
                    if (response.isSuccessful) {
                        val result = (response.body() as NewsListt.NewsList)
                        Log.d("API FETCH", "API FETCH SUCCESSFUL")
                        print(Gson().toJson(response))
                        _newsArticleLiveData.postValue(result)
                    }
                }
            }
        }

}

//class NewsFeedViewModelFactory(private val database: NewsArticlesDao, private val application: Application): ViewModelProvider.NewInstanceFactory() {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T = NewsFeedViewModel(database, application) as T
//}