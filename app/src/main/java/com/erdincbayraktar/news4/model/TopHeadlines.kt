package com.erdincbayraktar.news4.model

data class TopHeadlines (
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)