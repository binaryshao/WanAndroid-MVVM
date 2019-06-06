package com.sbingo.wanandroid_mvvm.paging.source

import com.sbingo.wanandroid_mvvm.base.paging.BaseItemKeyedDataSource
import com.sbingo.wanandroid_mvvm.data.http.HttpManager
import com.sbingo.wanandroid_mvvm.model.Navigation
import com.sbingo.wanandroid_mvvm.utils.asyncSubscribe

/**
 * Author: Sbingo666
 * Date:   2019/4/28
 */
class NavigationDataSource(private val httpManager: HttpManager) : BaseItemKeyedDataSource<Navigation>() {


    override fun setKey(item: Navigation) = item.cid

    override fun onLoadAfter(params: LoadParams<Int>, callback: LoadCallback<Navigation>) {
        loadMoreSuccess()
    }

    override fun onLoadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Navigation>) {
        httpManager.wanApi.getProjectArticles()
            .asyncSubscribe({
                refreshSuccess(it.data!!.isEmpty())
                callback.onResult(it.data!!)
            }, {
                refreshFailed(it.message, params, callback)
            })
    }
}