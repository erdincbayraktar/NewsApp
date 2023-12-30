package com.erdincbayraktar.news4.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdincbayraktar.news4.model.Article
import com.erdincbayraktar.news4.model.BookmarkedArticle
import com.erdincbayraktar.news4.model.TopHeadlines
import com.erdincbayraktar.news4.repository.local.LocalRepository
import com.erdincbayraktar.news4.repository.remote.RemoteRepository
import com.erdincbayraktar.news4.util.Mapper
import com.erdincbayraktar.news4.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
    private val mapper: Mapper
) : ViewModel() {

    private val topHeadlines = MutableLiveData<Resource<TopHeadlines>>()
    val topHeadlinesLiveData: LiveData<Resource<TopHeadlines>>
        get() = topHeadlines

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("CoroutineExceptionHandler Error: ${throwable.localizedMessage}")
    }

    fun getNewsDataFromNetwork() {
        topHeadlines.value = Resource.loading(null)
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val response= remoteRepository.downloadNews()
            withContext(Dispatchers.Main) {
                topHeadlines.value = response
            }
        }
    }

    fun insertArticleToBookmarks(article: Article) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val bookmarkedArticle : BookmarkedArticle = mapper.map(article)
            localRepository.insertBookmarkedArticle(bookmarkedArticle)
        }
    }

    fun deleteBookmarkedArticle(article: Article) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val bookmarkedArticle : BookmarkedArticle = mapper.map(article)
            localRepository.deleteBookmarkedArticle(bookmarkedArticle)
        }
    }

}
