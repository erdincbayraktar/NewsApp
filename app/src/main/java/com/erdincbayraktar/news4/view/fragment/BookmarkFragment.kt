package com.erdincbayraktar.news4.view.fragment

import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erdincbayraktar.news4.databinding.FragmentBookmarkBinding
import com.erdincbayraktar.news4.model.BookmarkedArticle
import com.erdincbayraktar.news4.view.adapter.BookmarkRecyclerAdapter
import com.erdincbayraktar.news4.viewmodel.BookmarkViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BookmarkFragment @Inject constructor(
    private val bookmarkRecyclerAdapter: BookmarkRecyclerAdapter
) : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!
    private val bookmarkViewModel: BookmarkViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBookmarkBinding.inflate(inflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBookmarkRecyclerView()
        bookmarkViewModel.getBookmarkedDataFromLocalDb()
        observeBookmarkData()
        initBookmarkSwipeListener()
    }

    private fun setupBookmarkRecyclerView() {
        binding.bookmarkRecyclerView.adapter = bookmarkRecyclerAdapter
        binding.bookmarkRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeBookmarkData() {
        bookmarkViewModel.bookmarkedArticleListLiveData.observe(viewLifecycleOwner) {
            bookmarkRecyclerAdapter.bookmarkedArticleList = it
        }
    }

    private fun initBookmarkSwipeListener() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

                //silerken arka plan kirmizi olsun, delete icon'u olsun
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val layoutPosition = viewHolder.layoutPosition
                val swipedArticle = bookmarkRecyclerAdapter.bookmarkedArticleList[layoutPosition]

                val bookmarkedArticleListEdited = bookmarkRecyclerAdapter.bookmarkedArticleList.toMutableList()
                bookmarkedArticleListEdited.removeAt(layoutPosition)
                bookmarkRecyclerAdapter.bookmarkedArticleList = bookmarkedArticleListEdited.toList()

                showSnackbarOnSwipeAction(swipedArticle, layoutPosition, bookmarkedArticleListEdited)
            }
        }).attachToRecyclerView(binding.bookmarkRecyclerView)
    }

    private fun showSnackbarOnSwipeAction(swipedArticle: BookmarkedArticle, layoutPosition: Int, bookmarkedArticleListEdited: MutableList<BookmarkedArticle>) { Snackbar
        .make(binding.bookmarkRecyclerView,"Deleted: ${swipedArticle.bookmarkedTitle}", Snackbar.LENGTH_LONG)
        .setAction("Undo") {
            bookmarkedArticleListEdited.add(layoutPosition,swipedArticle)
            bookmarkRecyclerAdapter.bookmarkedArticleList = bookmarkedArticleListEdited.toList()
        }.addCallback(object : Snackbar.Callback() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                if (event == DISMISS_EVENT_TIMEOUT) {
                    bookmarkViewModel.deleteBookmarkedArticle(swipedArticle)
                }
            }
        }).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
