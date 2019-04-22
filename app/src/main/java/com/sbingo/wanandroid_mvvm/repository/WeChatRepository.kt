package com.sbingo.wanandroid_mvvm.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sbingo.wanandroid_mvvm.base.RequestState
import com.sbingo.wanandroid_mvvm.data.http.HttpManager
import com.sbingo.wanandroid_mvvm.data.http.HttpResponse
import com.sbingo.wanandroid_mvvm.data.http.RxHttpObserver
import com.sbingo.wanandroid_mvvm.model.WXChapters
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Author: Sbingo666
 * Date:   2019/4/22
 */
class WeChatRepository(private val httpManager: HttpManager) {

    fun getWXChapters(): LiveData<RequestState<List<WXChapters>>> {
        val liveData = MutableLiveData<RequestState<List<WXChapters>>>()
        liveData.value = RequestState.loading()
        httpManager.wanApi.getWXChapters()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : RxHttpObserver<HttpResponse<List<WXChapters>>>() {
                override fun onNext(it: HttpResponse<List<WXChapters>>) {
                    super.onNext(it)
                    liveData.postValue(RequestState.success(it.data))
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    liveData.postValue(RequestState.error(e.message))
                }
            })
        return liveData
    }
}