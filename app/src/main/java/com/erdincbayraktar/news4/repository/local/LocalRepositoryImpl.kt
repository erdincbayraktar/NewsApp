package com.erdincbayraktar.news4.repository.local

import androidx.lifecycle.LiveData
import com.erdincbayraktar.news4.model.Article
import com.erdincbayraktar.news4.model.BookmarkedArticle
import com.erdincbayraktar.news4.service.local.NewsDao
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val newsDao: NewsDao
) : LocalRepository {

    override suspend fun getBookmarkedArticles(): List<BookmarkedArticle> {
        return newsDao.getBookmarkedArticles()
    }

    override suspend fun insertBookmarkedArticle(bookmarkedArticle: BookmarkedArticle) {
        newsDao.insertBookmarkedArticle(bookmarkedArticle)
    }

    override suspend fun deleteBookmarkedArticle(bookmarkedArticle: BookmarkedArticle) {
        newsDao.deleteBookmarkedArticle(bookmarkedArticle)
    }

}