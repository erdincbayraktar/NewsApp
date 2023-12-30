package com.erdincbayraktar.news4.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.erdincbayraktar.news4.databinding.ItemStoryHorizontalBinding
import com.erdincbayraktar.news4.model.Article
import com.erdincbayraktar.news4.model.BookmarkedArticle
import javax.inject.Inject

class BookmarkRecyclerAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<BookmarkRecyclerAdapter.ArticleHolder>() {

    class ArticleHolder(val binding: ItemStoryHorizontalBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<BookmarkedArticle>() {
        override fun areItemsTheSame(oldItem: BookmarkedArticle, newItem: BookmarkedArticle): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: BookmarkedArticle, newItem: BookmarkedArticle): Boolean {
            return oldItem == newItem
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

    var bookmarkedArticleList: List<BookmarkedArticle>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        val itemStoryHorizontalBinding = ItemStoryHorizontalBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArticleHolder(itemStoryHorizontalBinding)
    }

    override fun getItemCount(): Int {
        return bookmarkedArticleList.size
    }

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        holder.binding.apply {
            val currentBookmarkedArticle = bookmarkedArticleList[position]
            itemStoryHorizontalTitle.text = currentBookmarkedArticle.bookmarkedTitle
            itemStoryHorizontalAuthor.text = currentBookmarkedArticle.bookmarkedAuthor
            glide.load(currentBookmarkedArticle.bookmarkedUrlToImage).into(itemStoryHorizontalImage)
        }
    }

}