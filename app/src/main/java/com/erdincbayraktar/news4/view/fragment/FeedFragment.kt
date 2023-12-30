package com.erdincbayraktar.news4.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.erdincbayraktar.news4.databinding.FragmentFeedBinding
import com.erdincbayraktar.news4.util.Status
import com.erdincbayraktar.news4.view.adapter.FeedRecyclerAdapter
import com.erdincbayraktar.news4.viewmodel.FeedViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FeedFragment @Inject constructor(
    private val feedRecyclerAdapter: FeedRecyclerAdapter
) : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!
    private val feedViewModel: FeedViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFeedRecyclerView()
        initBookmarkButtonListener()
        initStoryLayoutListener()
        initFeedRefreshListener()
        feedViewModel.getNewsDataFromNetwork()
        observeFeedData()
    }

    private fun setupFeedRecyclerView() {
        binding.feedRecyclerView.adapter = feedRecyclerAdapter
        binding.feedRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initBookmarkButtonListener() {
        feedRecyclerAdapter.setOnBookmarkButtonClickListener {article, holder ->
            //val currentlyBookmarked = article.isBookmarked
            //val updatedArticle = article.copy(isBookmarked = !currentlyBookmarked)
            //feedViewModel.updateBlaBlaBla(updatedArticle)

            if(article.isBookmarked) {
                println("to be deleted: ${article.isBookmarked}")
                feedViewModel.deleteBookmarkedArticle(article)
            } else {
                println("to be inserted: ${article.isBookmarked}")
                feedViewModel.insertArticleToBookmarks(article)
            }

        }
    }

    private fun initStoryLayoutListener() {
        feedRecyclerAdapter.setOnStoryLayoutClickListener {article ->
            findNavController().navigate(FeedFragmentDirections.actionFeedFragmentToDetailFragment(article))
        }
    }

    private fun initFeedRefreshListener() {
        binding.feedSwipeRefreshLayout.setOnRefreshListener {
            feedViewModel.getNewsDataFromNetwork()
            Toast.makeText(requireContext(),"Data from API",Toast.LENGTH_SHORT).show()
            binding.feedSwipeRefreshLayout.isRefreshing = false
        }
    }

    private fun observeFeedData() {
        feedViewModel.topHeadlinesLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { topHeadlines ->
                        feedRecyclerAdapter.articleList = topHeadlines.articles
                    }
                    binding.feedProgressBar.visibility = View.GONE
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message ?: "Error!", Toast.LENGTH_LONG).show()
                    binding.feedProgressBar.visibility = View.GONE
                }

                Status.LOADING -> {
                    binding.feedProgressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
