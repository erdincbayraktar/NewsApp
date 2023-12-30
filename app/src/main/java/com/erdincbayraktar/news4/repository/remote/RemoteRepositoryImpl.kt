package com.erdincbayraktar.news4.repository.remote

import com.erdincbayraktar.news4.model.TopHeadlines
import com.erdincbayraktar.news4.service.remote.NewsApi
import com.erdincbayraktar.news4.util.Resource
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi
) : RemoteRepository {

    override suspend fun downloadNews(): Resource<TopHeadlines> {
        return try {
            val apiResponse = newsApi.getTopHeadLines()
            if(apiResponse.isSuccessful) {
                apiResponse.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error: Null data!",null)
            } else {
                Resource.error("Error: API response is failed!",null)
            }
        } catch (e: Exception) {
            Resource.error("Error: $e",null)
        }
    }
}