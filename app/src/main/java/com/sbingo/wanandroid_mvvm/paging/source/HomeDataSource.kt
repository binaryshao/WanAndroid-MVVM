package com.sbingo.wanandroid_mvvm.paging.source

import com.sbingo.wanandroid_mvvm.base.paging.BaseItemKeyedDataSource
import com.sbingo.wanandroid_mvvm.data.http.HttpManager
import com.sbingo.wanandroid_mvvm.data.http.HttpResponse
import com.sbingo.wanandroid_mvvm.model.Article
import com.sbingo.wanandroid_mvvm.model.Banner
import com.sbingo.wanandroid_mvvm.model.Page
import com.sbingo.wanandroid_mvvm.utils.asyncSubscribe
import io.reactivex.Observable
import io.reactivex.functions.Function3

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
                loadMoreSuccess()
                callback.onResult(it.data?.datas!!)
            }, {
                loadMoreFailed(it.message, params, callback)
            })
    }

    override fun onLoadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Article>) {
        Observable.zip(httpManager.wanApi.getBanner(),
            httpManager.wanApi.getTopArticles(),
            httpManager.wanApi.getArticles(pageNo),
            Function3<HttpResponse<List<Banner>>, HttpResponse<List<Article>>, HttpResponse<Page<Article>>, HttpResponse<Page<Article>>>
            { t1, t2, t3 ->
                t1.data?.let {
                    //动态构造一个 Article，将 banner 数据放入其中
                    val article = t3.data?.datas!![0]
                    article.bannerData = it
                    t3.data?.datas?.add(0, article)
                }
                t2.data?.let {
                    it.forEach { it.isTop = true }
                    t3.data?.datas?.addAll(1, it)
                }
                t3
            })
            .asyncSubscribe({
                pageNo = it.data?.curPage!!
                refreshSuccess(it.data?.datas!!.isEmpty())
                callback.onResult(it.data?.datas!!)
            }, {
                refreshFailed(it.message, params, callback)
            })
    }
}