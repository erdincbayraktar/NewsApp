package com.erdincbayraktar.news4.service.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.erdincbayraktar.news4.model.BookmarkedArticle

@Database(entities = [BookmarkedArticle::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}

