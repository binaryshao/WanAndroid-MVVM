package com.sbingo.wanandroid_mvvm.repository

import com.sbingo.wanandroid_mvvm.data.http.HttpManager
import rx.android.schedulers.AndroidSchedulers
import rx.plugins.RxJavaHooks.onError
import rx.schedulers.Schedulers

/**
 * Author: Sbingo666
 * Date:   2019/4/4
 */
class MainRepository(val httpManager: HttpManager) {

    companion object {
        @Volatile
        private var instance: MainRepository? = null

        fun getInstance(httpManager: HttpManager) =
            instance ?: synchronized(this) {
                instance ?: MainRepository(httpManager).also { instance = it }
            }
    }

    fun getArticles() = httpManager.wanApi.getArticles(1)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new RxHttpObserver < HttpResult < LoginResult > > () {
            @Override
            public void onCompleted() {
                mView.complete();
            }

            @Override
            public void onNext(HttpResult<LoginResult> result) {
                mView.loginSuccess(result.getResult());
            }

            @Override
            public void onError(Throwable e) {
                mView.showError(e.getMessage());
            }
        });
}
