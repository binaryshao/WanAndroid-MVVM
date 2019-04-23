package com.sbingo.wanandroid_mvvm.utils

import com.sbingo.wanandroid_mvvm.data.http.RxHttpObserver
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Author: Sbingo666
 * Date:   2019/4/23
 */

fun <T> Observable<T>.async(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.asyncSubscribe(onNext: (T) -> Unit, onError: (Throwable) -> Unit) {
    this.async()
        .subscribe(object : RxHttpObserver<T>() {
            override fun onNext(it: T) {
                super.onNext(it)
                onNext(it)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                onError(e)
            }
        })
}