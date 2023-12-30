package com.erdincbayraktar.news4.service.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.erdincbayraktar.news4.model.Article
import com.erdincbayraktar.news4.model.BookmarkedArticle

@Dao
interface NewsDao {

    @Query("SELECT * FROM bookmarked_articles")
    fun getBookmarkedArticles(): List<BookmarkedArticle>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmarkedArticle(bookmarkedArticle: BookmarkedArticle)

    @Delete
    suspend fun deleteBookmarkedArticle(bookmarkedArticle: BookmarkedArticle)

}
