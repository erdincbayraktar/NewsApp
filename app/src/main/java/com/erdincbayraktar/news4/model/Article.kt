package com.erdincbayraktar.news4.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Article(
    val source: Source,
    val author: String,
    val title: String,
    val description: String,
    @SerializedName("url")
    val urlToWebsite: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String,
    val isBookmarked: Boolean = false
) : Serializable
