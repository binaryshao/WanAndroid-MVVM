package com.sbingo.wanandroid_mvvm.data.http

import com.sbingo.wanandroid_mvvm.R
import com.sbingo.wanandroid_mvvm.WanApplication
import com.sbingo.wanandroid_mvvm.utils.ExecutorUtils
import com.sbingo.wanandroid_mvvm.utils.NetUtils
import com.sbingo.wanandroid_mvvm.utils.ToastUtils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class RxHttpObserver<T> : Observer<T> {


    override fun onSubscribe(d: Disposable) {
        if (!NetUtils.isConnected(WanApplication.instance)) {
            onError(RuntimeException(WanApplication.instance.getString(R.string.network_error)))
        }
    }

    override fun onError(e: Throwable) {
        e.message?.let {
            ExecutorUtils.main_thread(Runnable { ToastUtils.show(it) })
        }
    }

    override fun onComplete() {
    }
}
