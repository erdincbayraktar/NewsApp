package com.erdincbayraktar.news4.view.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.erdincbayraktar.news4.view.adapter.BookmarkRecyclerAdapter
import com.erdincbayraktar.news4.view.adapter.FeedRecyclerAdapter
import com.erdincbayraktar.news4.view.fragment.BookmarkFragment
import com.erdincbayraktar.news4.view.fragment.FeedFragment
import javax.inject.Inject

class NewsFragmentFactory @Inject constructor(
    private val feedRecyclerAdapter: FeedRecyclerAdapter,
    private val bookmarkRecyclerAdapter: BookmarkRecyclerAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            FeedFragment::class.java.name -> FeedFragment(feedRecyclerAdapter)
            BookmarkFragment::class.java.name -> BookmarkFragment(bookmarkRecyclerAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }

}