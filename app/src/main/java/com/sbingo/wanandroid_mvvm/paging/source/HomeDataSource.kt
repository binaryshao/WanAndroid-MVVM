package com.sbingo.wanandroid_mvvm.paging.source

import com.sbingo.wanandroid_mvvm.base.paging.BaseItemKeyedDataSource
import com.sbingo.wanandroid_mvvm.data.http.HttpManager
import com.sbingo.wanandroid_mvvm.data.http.HttpResponse
import com.sbingo.wanandroid_mvvm.model.Article
import com.sbingo.wanandroid_mvvm.model.Page
import com.sbingo.wanandroid_mvvm.utils.asyncSubscribe
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

/**
 * Author: Sbingo666
 * Date:   2019/4/17
 */
class HomeDataSource(private val httpManager: HttpManager) : BaseItemKeyedDataSource<Article>() {

    var pageNo = 0

    override fun setKey(item: Article) = item.id

    override fun onLoadAfter(params: LoadParams<Int>, callback: LoadCallback<Article>) {
        httpManager.wanApi.getArticles(pageNo)
            .asyncSubscribe({
                pageNo = it.data?.curPage!!
                networkSuccess()
                callback.onResult(it.data?.datas!!)
            }, {
                networkFailed(it.message, params, callback)
            })
    }

    override fun onLoadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Article>) {
        Observable.zip(httpManager.wanApi.getTopArticles(), httpManager.wanApi.getArticles(pageNo),
            BiFunction<HttpResponse<List<Article>>, HttpResponse<Page<Article>>, HttpResponse<Page<Article>>> { t1, t2 ->
                t1.data?.let {
                    it.forEach { it.isTop = true }
                    t2.data?.datas?.addAll(0, it)
                }
                t2
            })
            .asyncSubscribe({
                pageNo = it.data?.curPage!!
                refreshSuccess()
                callback.onResult(it.data?.datas!!)
            }, {
                refreshFailed(it.message, params, callback)
            })
    }
}