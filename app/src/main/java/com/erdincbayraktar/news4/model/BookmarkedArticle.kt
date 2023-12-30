package com.erdincbayraktar.news4.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarked_articles")
data class BookmarkedArticle(
    val bookmarkedAuthor: String,
    val bookmarkedTitle: String,
    val bookmarkedDescription: String,
    @PrimaryKey
    val bookmarkedUrlToWebsite: String,
    val bookmarkedUrlToImage: String,
    val bookmarkedPublishedAt: String,
    val bookmarkedContent: String
)