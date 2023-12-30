package com.erdincbayraktar.news4.util

import com.erdincbayraktar.news4.model.Article
import com.erdincbayraktar.news4.model.BookmarkedArticle
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Mapper @Inject constructor() {

    fun map(article: Article) = BookmarkedArticle(
            bookmarkedAuthor = article.author,
            bookmarkedTitle = article.title,
            bookmarkedDescription = article.description,
            bookmarkedUrlToWebsite = article.urlToWebsite,
            bookmarkedUrlToImage = article.urlToImage,
            bookmarkedPublishedAt = article.publishedAt,
            bookmarkedContent = article.content
        )

}

