package com.byjus.assignment.byjusAssignment.fragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.byjus.assignment.byjusAssignment.database.NewsArticle
import com.byjus.assignment.byjusAssignment.database.NewsArticlesDao
import com.byjus.assignment.byjusAssignment.model.NewsListt
import com.byjus.assignment.byjusAssignment.rest.ApiClient
import com.byjus.assignment.byjusAssignment.rest.ApiInterface
import com.google.gson.Gson
import kotlinx.coroutines.*
import retrofit2.await
import retrofit2.awaitResponse

class NewsFeedViewModel(val database: NewsArticlesDao, application: Application) :ViewModel() {

    var _newsArticleLiveData = MutableLiveData<NewsListt.NewsList>()
    val newsArticleLiveData: LiveData<NewsListt.NewsList> get() = _newsArticleLiveData
    var _newsArticlesDb = MutableLiveData<List<NewsArticle>>()
    val newsArticlesDb : LiveData<List<NewsArticle>> get() = _newsArticlesDb


    fun getArticles(country: String, category: String, apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("API FETCH", "API FETCH STARTED")

            val response = ApiClient.getClient().create(ApiInterface::class.java)
                .getLatestNews(country, category, apiKey).awaitResponse()
            if (response.isSuccessful) {
                val result = (response.body() as NewsListt.NewsList)
                Log.d("API FETCH", "API FETCH SUCCESSFUL")
                _newsArticleLiveData.postValue(result)
            }
        }
    }

    val job = Job()
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    fun insertArticlesIntoDB(article: NewsArticle) {
        uiScope.launch {
            insert(article)
        }
    }

    private suspend fun insert(article: NewsArticle) {
        withContext(Dispatchers.IO) {
            database.insertArticle(article)
        }
    }

    fun clearDb() {
        uiScope.launch {
            clearNewsDb()
        }
    }

    private suspend fun clearNewsDb() {
        withContext(Dispatchers.IO) {
            database.clearDataBase()
        }
    }

    fun getAllArticles() {
         uiScope.launch {
             val list = getAllArticlesDb()
             _newsArticlesDb.postValue(list?.value)
         }
    }

    private suspend fun getAllArticlesDb(): LiveData<List<NewsArticle>>? {
        return withContext(Dispatchers.IO) {
            val list = database.getAllArticles()
            list
        }
    }

}

class NewsFeedViewModelFactory(private val database: NewsArticlesDao, private val application: Application): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = NewsFeedViewModel(database, application) as T
}