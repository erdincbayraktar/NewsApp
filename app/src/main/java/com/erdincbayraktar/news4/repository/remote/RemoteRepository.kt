package com.erdincbayraktar.news4.repository.remote

import com.erdincbayraktar.news4.model.TopHeadlines
import com.erdincbayraktar.news4.util.Resource

interface RemoteRepository {

    suspend fun downloadNews() : Resource<TopHeadlines>

}