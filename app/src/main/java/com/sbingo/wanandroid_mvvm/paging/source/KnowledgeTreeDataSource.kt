package com.sbingo.wanandroid_mvvm.paging.source

import com.sbingo.wanandroid_mvvm.base.paging.BaseItemKeyedDataSource
import com.sbingo.wanandroid_mvvm.data.http.HttpManager
import com.sbingo.wanandroid_mvvm.model.Chapter
import com.sbingo.wanandroid_mvvm.utils.asyncSubscribe

/**
 * Author: Sbingo666
 * Date:   2019/4/26
 */
class KnowledgeTreeDataSource(private val httpManager: HttpManager) : BaseItemKeyedDataSource<Chapter>() {


    override fun setKey(item: Chapter) = item.id

    override fun onLoadAfter(params: LoadParams<Int>, callback: LoadCallback<Chapter>) {
        loadMoreSuccess()
    }

    override fun onLoadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Chapter>) {
        httpManager.wanApi.getKnowledgeTree()
            .asyncSubscribe({
                refreshSuccess(it.data!!.isEmpty())
                callback.onResult(it.data!!)
            }, {
                refreshFailed(it.message, params, callback)
            })
    }
}