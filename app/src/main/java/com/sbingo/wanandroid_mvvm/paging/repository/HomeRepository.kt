package com.sbingo.wanandroid_mvvm.paging.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sbingo.wanandroid_mvvm.base.RequestState
import com.sbingo.wanandroid_mvvm.base.paging.BaseDataSourceFactory
import com.sbingo.wanandroid_mvvm.base.paging.BasePagingRepository
import com.sbingo.wanandroid_mvvm.data.http.HttpManager
import com.sbingo.wanandroid_mvvm.model.Article
import com.sbingo.wanandroid_mvvm.model.Banner
import com.sbingo.wanandroid_mvvm.paging.factory.HomeDataSourceFactory
import com.sbingo.wanandroid_mvvm.utils.asyncSubscribe

/**
 * Author: Sbingo666
 * Date:   2019/4/17
 */
class HomeRepository(private val httpManager: HttpManager) : BasePagingRepository<Article>() {

    override fun createDataBaseFactory(): BaseDataSourceFactory<Article> {
        return HomeDataSourceFactory(httpManager)
    }

    fun getBanner(): LiveData<RequestState<List<Banner>>> {
        val liveData = MutableLiveData<RequestState<List<Banner>>>()
        liveData.value = RequestState.loading()
        httpManager.wanApi.getBanner()
            .asyncSubscribe({
                liveData.postValue(RequestState.success(it.data))
            }, {
                liveData.postValue(RequestState.error(it.message))
            })
        return liveData
    }

}