package com.sbingo.wanandroid_mvvm.paging.source

import com.sbingo.wanandroid_mvvm.base.paging.BaseItemKeyedDataSource
import com.sbingo.wanandroid_mvvm.data.http.HttpManager
import com.sbingo.wanandroid_mvvm.data.http.HttpResponse
import com.sbingo.wanandroid_mvvm.data.http.RxHttpObserver
import com.sbingo.wanandroid_mvvm.model.Article
import com.sbingo.wanandroid_mvvm.model.ArticlePages
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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
            .subscribe(object : RxHttpObserver<HttpResponse<ArticlePages>>() {
                override fun onComplete() {
                }

                override fun onNext(it: HttpResponse<ArticlePages>) {
                    pageNo = it.data?.curPage!!
                    networkSuccess()
                    callback.onResult(it.data?.datas!!)
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    networkFailed(e.message, params, callback)
                }
            })
    }

    override fun onLoadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Article>) {
        httpManager.wanApi.getArticles(pageNo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : RxHttpObserver<HttpResponse<ArticlePages>>() {
                override fun onComplete() {
                }

                override fun onNext(it: HttpResponse<ArticlePages>) {
                    pageNo = it.data?.curPage!!
                    refreshSuccess()
                    callback.onResult(it.data?.datas!!)
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    refreshFailed(e.message, params, callback)
                }
            })
    }
}