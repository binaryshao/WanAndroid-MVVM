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
 * Date:   2019/4/23
 */
class WXDataSource(private val httpManager: HttpManager, private val wxId: Int) : BaseItemKeyedDataSource<Article>() {

    var pageNo = 1

    override fun setKey(item: Article) = item.id

    override fun onLoadAfter(params: LoadParams<Int>, callback: LoadCallback<Article>) {
        httpManager.wanApi.getWXArticles(wxId, pageNo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : RxHttpObserver<HttpResponse<ArticlePages>>() {
                override fun onNext(it: HttpResponse<ArticlePages>) {
                    pageNo += 1
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
        httpManager.wanApi.getWXArticles(wxId, pageNo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : RxHttpObserver<HttpResponse<ArticlePages>>() {
                override fun onNext(it: HttpResponse<ArticlePages>) {
                    pageNo += 1
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