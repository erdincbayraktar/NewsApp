package com.erdincbayraktar.news4.repository.local

import androidx.lifecycle.LiveData
import com.erdincbayraktar.news4.model.Article
import com.erdincbayraktar.news4.model.BookmarkedArticle

interface LocalRepository {
    suspend fun getBookmarkedArticles(): List<BookmarkedArticle>

    suspend fun insertBookmarkedArticle(bookmarkedArticle: BookmarkedArticle)

    suspend fun deleteBookmarkedArticle(bookmarkedArticle: BookmarkedArticle)

}