package com.sbingo.wanandroid_mvvm.base.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.sbingo.wanandroid_mvvm.base.RequestState
import com.sbingo.wanandroid_mvvm.utils.ExecutorUtils


/**
 * Author: Sbingo666
 * Date:   2019/4/12
 */
abstract class BaseItemKeyedDataSource<T> : ItemKeyedDataSource<Int, T>() {
    private var retry: (() -> Any)? = null
    private var retryExecutor = ExecutorUtils.NETWORK_IO

     val networkStatus by lazy {
        MutableLiveData<RequestState<String>>()
    }

    val refreshStatus by lazy {
        MutableLiveData<RequestState<String>>()
    }

    fun retryFailed() {
        val preRetry = retry
        retry = null
        preRetry.let {
            retryExecutor.execute {
                it?.invoke()
            }
        }
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<T>) {
        refreshStatus.postValue(RequestState.loading())
        onLoadInitial(params, callback)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<T>) {
        networkStatus.postValue(RequestState.loading())
        onLoadAfter(params, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<T>) {
    }

    fun refreshSuccess() {
        refreshStatus.postValue(RequestState.success())
        retry = null
    }

    fun networkSuccess() {
        retry = null
        networkStatus.postValue(RequestState.success())
    }

    fun networkFailed(msg: String?, params: LoadParams<Int>, callback: LoadCallback<T>) {
        networkStatus.postValue(RequestState.error(msg))
        retry = {
            loadAfter(params, callback)
        }
    }

    fun refreshFailed(msg: String?, params: LoadInitialParams<Int>, callback: LoadInitialCallback<T>) {
        refreshStatus.postValue(RequestState.error(msg))
        retry = {
            loadInitial(params, callback)
        }
    }


    override fun getKey(item: T) = setKey(item)

    abstract fun setKey(item: T): Int

    abstract fun onLoadAfter(params: LoadParams<Int>, callback: LoadCallback<T>)

    abstract fun onLoadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<T>)
}


