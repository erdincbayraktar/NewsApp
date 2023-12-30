package com.erdincbayraktar.news4.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.erdincbayraktar.news4.R
import com.erdincbayraktar.news4.repository.local.LocalRepository
import com.erdincbayraktar.news4.repository.local.LocalRepositoryImpl
import com.erdincbayraktar.news4.repository.remote.RemoteRepository
import com.erdincbayraktar.news4.repository.remote.RemoteRepositoryImpl
import com.erdincbayraktar.news4.service.local.NewsDao
import com.erdincbayraktar.news4.service.local.NewsDatabase
import com.erdincbayraktar.news4.service.remote.NewsApi
import com.erdincbayraktar.news4.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectNewsAPI() : NewsApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(NewsApi::class.java)
    }

    @Singleton
    @Provides
    fun injectRemoteRepository(newsApi: NewsApi)
        = RemoteRepositoryImpl(newsApi) as RemoteRepository

    @Singleton
    @Provides
    fun injectLocalRepository(newsDao: NewsDao)
    = LocalRepositoryImpl(newsDao) as LocalRepository

    @Singleton
    @Provides
    fun injectNewsDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, NewsDatabase::class.java,"NewsRoomDatabase").build()

    @Singleton
    @Provides
    fun injectNewsDao(
        database: NewsDatabase
    ) = database.newsDao()

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide
        .with(context).setDefaultRequestOptions(RequestOptions()
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
        )

}