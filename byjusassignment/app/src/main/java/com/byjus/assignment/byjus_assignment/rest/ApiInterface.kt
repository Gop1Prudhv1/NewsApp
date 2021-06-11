package com.byjus.assignment.byjus_assignment.rest

import com.byjus.assignment.byjus_assignment.model.NewsList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("top-headlines")
    fun getLatestNews(@Query("country") country: String, @Query("category") category: String,
                      @Query("apiKey") apiKey:String)
    : Call<NewsList.NewsList>
}