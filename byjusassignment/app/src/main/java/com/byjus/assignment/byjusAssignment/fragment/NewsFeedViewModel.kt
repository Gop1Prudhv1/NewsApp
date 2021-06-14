package com.byjus.assignment.byjusAssignment.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.byjus.assignment.byjusAssignment.database.NewsArticle
import com.byjus.assignment.byjusAssignment.database.NewsArticlesDatabase
import com.byjus.assignment.byjusAssignment.model.NewsListt
import com.byjus.assignment.byjusAssignment.rest.ApiClient
import com.byjus.assignment.byjusAssignment.rest.ApiInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(private val database: NewsArticlesDatabase) :
    ViewModel() {

    private var _newsArticleLiveData = MutableLiveData<NewsListt.NewsList>()
    val newsArticleLiveData: LiveData<NewsListt.NewsList> get() = _newsArticleLiveData

    private var newsList = mutableListOf<NewsListt.Article>()

    private val allArticlesDb = database.newsArticlesDao.getAllArticles()

    val convertedArticlesDb = Transformations.map(allArticlesDb) {
        it?.forEach { newsArticle ->
            val source = NewsListt.Source(
                id = "",
                name = newsArticle.source ?: ""
            )
            val article = NewsListt.Article(
                source = source,
                author = newsArticle.author ?: "",
                title = newsArticle.description ?: "",
                description = newsArticle.description ?: "",
                url = newsArticle.url ?: "",
                urlToImage = newsArticle.urlToImage ?: "",
                publishedAt = newsArticle.publishedAt ?: "",
                content = newsArticle.content ?: ""
            )
            newsList.add(article)
        }
        return@map NewsListt.NewsList(
            status = "",
            totalResults = 0,
            articles = newsList
        )
    }

    companion object {
        @RequiresApi(Build.VERSION_CODES.M)
        fun networkConnectivity(context: Context): Boolean {
            val cm = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnectedOrConnecting
        }

    }


    fun getArticles(country: String, category: String, apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("API FETCH", "API FETCH STARTED")

            val response = ApiClient.getClient().create(ApiInterface::class.java)
                .getLatestNews(country, category, apiKey)
            if (response.isSuccessful) {
                val result = (response.body() as NewsListt.NewsList)
                Log.d("API FETCH", "API FETCH SUCCESSFUL")
                _newsArticleLiveData.postValue(result)

                clearDb()

                for (article in result.articles) {
                    val newsArticle = NewsArticle(
                        source = article.source.name,
                        author = article.author,
                        title = article.title,
                        description = article.description,
                        url = article.url,
                        urlToImage = article.urlToImage,
                        publishedAt = article.publishedAt,
                        content = article.content
                    )
                    insertArticlesIntoDB(newsArticle)
                }
            }
        }
    }

    val job = Job()
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private fun insertArticlesIntoDB(article: NewsArticle) {
        uiScope.launch {
            insert(article)
        }
    }

    private suspend fun insert(article: NewsArticle) {
        withContext(Dispatchers.IO) {
            database.newsArticlesDao.insertArticle(article)
        }
    }

    private fun clearDb() {
        uiScope.launch {
            clearNewsDb()
        }
    }

    private suspend fun clearNewsDb() {
        withContext(Dispatchers.IO) {
            database.newsArticlesDao.clearDataBase()
        }
    }


}
