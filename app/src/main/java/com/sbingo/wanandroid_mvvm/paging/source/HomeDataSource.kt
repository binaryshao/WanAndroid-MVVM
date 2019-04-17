package com.sbingo.wanandroid_mvvm.paging.source

import com.sbingo.wanandroid_mvvm.base.paging.BaseItemKeyedDataSource
import com.sbingo.wanandroid_mvvm.data.http.HttpManager
import com.sbingo.wanandroid_mvvm.model.Article
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Author: Sbingo666
 * Date:   2019/4/17
 */
class HomeDataSource(private val httpManager: HttpManager) : BaseItemKeyedDataSource<Article>() {

    var pageNo = 0

    override fun setKey(item: Article) = item.id

    override fun onLoadAfter(params: LoadParams<Int>, callback: LoadCallback<Article>) {
        httpManager.wanApi.getArticles(pageNo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                pageNo = it.data?.curPage!!
                networkSuccess()
                callback.onResult(it.data?.datas!!)
            }, {
                networkFailed(it.message, params, callback)
            })
    }

    override fun onLoadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Article>) {
        httpManager.wanApi.getArticles(pageNo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                pageNo = it.data?.curPage!!
                refreshSuccess()
                networkSuccess()
                callback.onResult(it.data?.datas!!)
            }, {
                refreshFailed(it.message, params, callback)
            })
    }
}