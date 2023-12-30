package com.erdincbayraktar.news4.service.remote

import com.erdincbayraktar.news4.model.TopHeadlines
import com.erdincbayraktar.news4.util.Constants.API_KEY
import com.erdincbayraktar.news4.util.Constants.COUNTRY
import com.erdincbayraktar.news4.util.Constants.PAGE_SIZE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("/v2/top-headlines")
    suspend fun getTopHeadLines(
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("country") country: String = COUNTRY,
        @Query("pageSize") pageSize: Int = PAGE_SIZE
    ) : Response<TopHeadlines>

}