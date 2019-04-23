package com.sbingo.wanandroid_mvvm.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sbingo.wanandroid_mvvm.base.RequestState
import com.sbingo.wanandroid_mvvm.data.http.HttpManager
import com.sbingo.wanandroid_mvvm.model.Chapter
import com.sbingo.wanandroid_mvvm.utils.asyncSubscribe

/**
 * Author: Sbingo666
 * Date:   2019/4/22
 */
class WeChatRepository(private val httpManager: HttpManager) {

    fun getWXChapters(): LiveData<RequestState<List<Chapter>>> {
        val liveData = MutableLiveData<RequestState<List<Chapter>>>()
        liveData.value = RequestState.loading()
        httpManager.wanApi.getWXChapters()
            .asyncSubscribe({
                liveData.postValue(RequestState.success(it.data))
            }, {
                liveData.postValue(RequestState.error(it.message))
            })
        return liveData
    }
}