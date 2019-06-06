package com.sbingo.wanandroid_mvvm.paging.source

import com.sbingo.wanandroid_mvvm.base.paging.BaseItemKeyedDataSource
import com.sbingo.wanandroid_mvvm.data.http.HttpManager
import com.sbingo.wanandroid_mvvm.model.Article
import com.sbingo.wanandroid_mvvm.utils.asyncSubscribe

/**
 * Author: Sbingo666
 * Date:   2019/4/26
 */
class KnowledgeDataSource(private val httpManager: HttpManager, private val knowledgeId: Int) : BaseItemKeyedDataSource<Article>() {

    var pageNo = 0

    override fun setKey(item: Article) = item.id

    override fun onLoadAfter(params: LoadParams<Int>, callback: LoadCallback<Article>) {
        httpManager.wanApi.getKnowledgeArticles(pageNo, knowledgeId)
            .asyncSubscribe({
                pageNo += 1
                loadMoreSuccess()
                callback.onResult(it.data?.datas!!)
            }, {
                loadMoreFailed(it.message, params, callback)
            })
    }

    override fun onLoadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Article>) {
        httpManager.wanApi.getKnowledgeArticles(pageNo, knowledgeId)
            .asyncSubscribe({
                pageNo += 1
                refreshSuccess(it.data?.datas!!.isEmpty())
                callback.onResult(it.data?.datas!!)
            }, {
                refreshFailed(it.message, params, callback)
            })
    }
}