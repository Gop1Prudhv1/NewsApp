package com.byjus.assignment.byjusAssignment.rest

import com.byjus.assignment.byjusAssignment.model.NewsListt
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("top-headlines")
    fun getLatestNews(@Query("country") country: String, @Query("category") category: String,
                      @Query("apiKey") apiKey:String)
    : Call<NewsListt.NewsList>
}