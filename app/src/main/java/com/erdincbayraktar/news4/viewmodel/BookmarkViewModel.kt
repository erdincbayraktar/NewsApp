package com.erdincbayraktar.news4.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdincbayraktar.news4.model.BookmarkedArticle
import com.erdincbayraktar.news4.repository.local.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val localRepository: LocalRepository
) : ViewModel() {

    private val bookmarkedArticleList = MutableLiveData<List<BookmarkedArticle>>()
    val bookmarkedArticleListLiveData: LiveData<List<BookmarkedArticle>>
        get() = bookmarkedArticleList

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error: ${throwable.localizedMessage}")
    }

    fun getBookmarkedDataFromLocalDb() {
        viewModelScope.launch (Dispatchers.IO + exceptionHandler) {
            val response = localRepository.getBookmarkedArticles()
            withContext(Dispatchers.Main) {
                bookmarkedArticleList.value = response
            }
        }
    }

    fun deleteBookmarkedArticle(bookmarkedArticle: BookmarkedArticle) {
        viewModelScope.launch (Dispatchers.IO + exceptionHandler) {
            localRepository.deleteBookmarkedArticle(bookmarkedArticle)
        }
    }

}