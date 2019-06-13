package com.sbingo.wanandroid_mvvm.ui.fragment

import android.graphics.Color
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.sbingo.wanandroid_mvvm.R
import com.sbingo.wanandroid_mvvm.adapter.HomeAdapter
import com.sbingo.wanandroid_mvvm.base.BaseFragment
import com.sbingo.wanandroid_mvvm.data.http.HttpManager
import com.sbingo.wanandroid_mvvm.paging.repository.WXRepository
import com.sbingo.wanandroid_mvvm.viewmodel.WXArticleViewModel
import kotlinx.android.synthetic.main.refresh_layout.*

/**
 * Author: Sbingo666
 * Date:   2019/4/22
 */
class WXArticleFragment(private val wxId: Int) : BaseFragment() {

    private val viewModel by lazy {
        ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repository = WXRepository(HttpManager.getInstance(), wxId)
                return WXArticleViewModel(repository) as T
            }
        })
            .get(WXArticleViewModel::class.java)
    }

    private val adapter by lazy {
        HomeAdapter { viewModel.retry() }
    }

    override var layoutId = R.layout.refresh_layout

    override fun initData() {
        multipleStatusView = multiple_status_view
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
                    swipeRefreshLayout.isRefreshing = false
                    when {
                        it.isLoading() ->
                            if (isRefreshFromPull) {
                                swipeRefreshLayout.isRefreshing = true
                                isRefreshFromPull = false
                            } else {
                                multipleStatusView?.showLoading()
                            }
                        it.isSuccess() ->
                            if (it.data!!) {
                                // refreshState 中的 data 表示数据是否为空
                                multipleStatusView?.showEmpty()
                            } else {
                                multipleStatusView?.showContent()
                            }
                        it.isError() -> multipleStatusView?.showError()
                    }
                })
            networkState.observe(viewLifecycleOwner, Observer {
                adapter.setRequestState(it)
            })
            initLoad()
        }
    }

    override fun onRetry() {
        viewModel.refresh()
    }

    private fun initSwipe() {
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE)
        swipeRefreshLayout.setOnRefreshListener {
            isRefreshFromPull = true
            viewModel.refresh()
        }
    }

    private fun initRecyclerView() {
        recyclerView.adapter = adapter
    }
}