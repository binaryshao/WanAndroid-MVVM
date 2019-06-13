package com.sbingo.wanandroid_mvvm.ui.fragment

import android.graphics.Color
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.classic.common.MultipleStatusView
import com.sbingo.wanandroid_mvvm.R
import com.sbingo.wanandroid_mvvm.adapter.HomeAdapter
import com.sbingo.wanandroid_mvvm.base.BaseFragment
import com.sbingo.wanandroid_mvvm.data.http.HttpManager
import com.sbingo.wanandroid_mvvm.paging.repository.HomeRepository
import com.sbingo.wanandroid_mvvm.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.refresh_layout.*

/**
 * Author: Sbingo666
 * Date:   2019/4/15
 */
class HomeFragment : BaseFragment() {

    private val viewModel by lazy {
        ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repository = HomeRepository(HttpManager.getInstance())
                return HomeViewModel(repository) as T
            }
        })
            .get(HomeViewModel::class.java)
    }

    private val adapter by lazy {
        HomeAdapter { viewModel.retry() }
    }

    override var layoutId = R.layout.refresh_layout

    override var multipleStatusView: MultipleStatusView? = null

    override fun initData() {
        initSwipe()
        initRecyclerView()
    }

    override fun subscribeUi() {
        viewModel.run {
            pagedList.observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
            })
            refreshState.observe(
                viewLifecycleOwner,
                Observer {
                    if (it.isLoading()) {
                        multipleStatusView?.showLoading()
                    } else if (it.isSuccess()) {
                        if (it.data!!) {
                            multipleStatusView?.showEmpty()
                        } else {
                            multipleStatusView?.showContent()
                        }
                    }
                })
            networkState.observe(viewLifecycleOwner, Observer {
                adapter.setRequestState(it)
            })
            setPageSize()
        }
    }

    override fun onRetry() {
        viewModel.refresh()
    }

    private fun initSwipe() {
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun initRecyclerView() {
        recyclerView.adapter = adapter
    }
}