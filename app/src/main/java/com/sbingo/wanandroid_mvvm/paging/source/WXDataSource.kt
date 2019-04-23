package com.sbingo.wanandroid_mvvm.paging.source

import com.sbingo.wanandroid_mvvm.base.paging.BaseItemKeyedDataSource
import com.sbingo.wanandroid_mvvm.data.http.HttpManager
import com.sbingo.wanandroid_mvvm.model.Article
import com.sbingo.wanandroid_mvvm.utils.asyncSubscribe

/**
 * Author: Sbingo666
 * Date:   2019/4/23
 */
class WXDataSource(private val httpManager: HttpManager, private val wxId: Int) : BaseItemKeyedDataSource<Article>() {

    var pageNo = 1

    override fun setKey(item: Article) = item.id

    override fun onLoadAfter(params: LoadParams<Int>, callback: LoadCallback<Article>) {
        httpManager.wanApi.getWXArticles(wxId, pageNo)
            .asyncSubscribe({
                pageNo += 1
                networkSuccess()
                callback.onResult(it.data?.datas!!)
            }, {
                networkFailed(it.message, params, callback)
            })
    }

    override fun onLoadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Article>) {
        httpManager.wanApi.getWXArticles(wxId, pageNo)
            .asyncSubscribe({
                pageNo += 1
                refreshSuccess()
                callback.onResult(it.data?.datas!!)
            }, {
                refreshFailed(it.message, params, callback)
            })
    }
}