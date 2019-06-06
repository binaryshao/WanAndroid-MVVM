package com.sbingo.wanandroid_mvvm.ui.fragment

import android.graphics.Color
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.orhanobut.logger.Logger
import com.sbingo.wanandroid_mvvm.R
import com.sbingo.wanandroid_mvvm.adapter.NavigationChapterAdapter
import com.sbingo.wanandroid_mvvm.adapter.NavigationWebsiteAdapter
import com.sbingo.wanandroid_mvvm.base.BaseFragment
import com.sbingo.wanandroid_mvvm.data.http.HttpManager
import com.sbingo.wanandroid_mvvm.paging.repository.NavigationRepository
import com.sbingo.wanandroid_mvvm.viewmodel.NavigationViewModel
import kotlinx.android.synthetic.main.fragment_navigation.*
import kotlinx.android.synthetic.main.refresh_layout.swipeRefreshLayout

/**
 * Author: Sbingo666
 * Date:   2019/4/15
 */
class NavigationFragment : BaseFragment() {

    private var checkedPosition: Int = 0

    private val viewModel by lazy {
        ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repository = NavigationRepository(HttpManager.getInstance())
                return NavigationViewModel(repository) as T
            }
        })
            .get(NavigationViewModel::class.java)
    }

    private val adapterChapter by lazy {
        NavigationChapterAdapter(
            { viewModel.retry() },
            object : NavigationChapterAdapter.OnItemClickListener {
                override fun onItemClicked(position: Int) {
                    position.let {
                        recyclerView_websites.scrollToPosition(it)
                        checkedPosition = it
                    }
                }
            }
        )
    }

    private val adapterWebsite by lazy {
        NavigationWebsiteAdapter { viewModel.retry() }
    }

    override var layoutId = R.layout.fragment_navigation

    override fun initData() {
        initSwipe()
        initRecyclerView()
    }

    override fun subscribeUi() {
        viewModel.run {
            pagedList.observe(viewLifecycleOwner, Observer {
                adapterChapter.submitList(it)
                adapterWebsite.submitList(it)
            })
            refreshState.observe(
                viewLifecycleOwner,
                Observer {
                    swipeRefreshLayout.isRefreshing = it.isLoading()
                    if (it.isLoading()) {
                        hideEmpty()
                    } else if (it.isSuccess() && it.data!!) {
                        showEmpty()
                    }
                })
            networkState.observe(viewLifecycleOwner, Observer {
                adapterChapter.setRequestState(it)
                adapterWebsite.setRequestState(it)
            })
            setPageSize()
        }
    }

    private fun initSwipe() {
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
            adapterChapter.setCheckedPosition(0)
        }
    }

    private fun initRecyclerView() {
        recyclerView_chapters.adapter = adapterChapter
        recyclerView_websites.run {
            adapter = adapterWebsite
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (newState == SCROLL_STATE_IDLE) {
                        val layoutManager = recyclerView_websites.layoutManager as LinearLayoutManager
                        val index = layoutManager.findFirstVisibleItemPosition()
                        if (checkedPosition != index) {
                            index.let {
                                Logger.d("改变导航 position:$checkedPosition --> $it")
                                recyclerView_chapters.scrollToPosition(it)
                                adapterChapter.setCheckedPosition(it)
                                checkedPosition = it
                            }
                        }
                    }
                }
            })
        }
    }

}