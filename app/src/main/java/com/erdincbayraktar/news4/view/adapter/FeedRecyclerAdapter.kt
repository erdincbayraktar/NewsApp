package com.erdincbayraktar.news4.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.erdincbayraktar.news4.R
import com.erdincbayraktar.news4.databinding.ItemStoryBlockBinding
import com.erdincbayraktar.news4.model.Article
import com.erdincbayraktar.news4.model.BookmarkedArticle
import com.erdincbayraktar.news4.repository.local.LocalRepository
import javax.inject.Inject


class FeedRecyclerAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<FeedRecyclerAdapter.ArticleHolder>() {

    class ArticleHolder(val binding: ItemStoryBlockBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

    var articleList: List<Article>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    private var onBookmarkButtonClickListener: ((Article, ArticleHolder) -> Unit) ? = null
    fun setOnBookmarkButtonClickListener (listener: (Article, ArticleHolder) -> Unit) {
        onBookmarkButtonClickListener = listener
    }

    private var onStoryLayoutClickListener: ((Article) -> Unit) ? = null
    fun setOnStoryLayoutClickListener (listener: (Article) -> Unit) {
        onStoryLayoutClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        val itemStoryBlockBinding = ItemStoryBlockBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArticleHolder(itemStoryBlockBinding)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        holder.binding.apply {
            val currentArticle = articleList[position]
            itemStoryBlockTitle.text = currentArticle.title
            itemStoryBlockAuthor.text = currentArticle.author
            glide.load(currentArticle.urlToImage).into(itemStoryBlockImage)

            if(currentArticle.isBookmarked) {
                itemStoryBlockButton.setImageResource(R.drawable.ic_bookmark_white)
            } else {
                itemStoryBlockButton.setImageResource(R.drawable.ic_bookmark_outline_white)
            }

            itemStoryBlockButton.setOnClickListener {
                onBookmarkButtonClickListener?.let {
                    it(currentArticle, holder)
                }
            }

            itemStoryBlockLayout.setOnClickListener {
                onStoryLayoutClickListener?.let {
                    it(currentArticle)
                }
            }

        }
    }

}