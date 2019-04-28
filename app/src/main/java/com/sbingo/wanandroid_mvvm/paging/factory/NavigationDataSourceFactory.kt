package com.sbingo.wanandroid_mvvm.paging.factory

import com.sbingo.wanandroid_mvvm.base.paging.BaseDataSourceFactory
import com.sbingo.wanandroid_mvvm.base.paging.BaseItemKeyedDataSource
import com.sbingo.wanandroid_mvvm.data.http.HttpManager
import com.sbingo.wanandroid_mvvm.model.Navigation
import com.sbingo.wanandroid_mvvm.paging.source.NavigationDataSource

/**
 * Author: Sbingo666
 * Date:   2019/4/28
 */
class NavigationDataSourceFactory(private val httpManager: HttpManager) :
    BaseDataSourceFactory<Navigation>() {

    override fun createDataSource(): BaseItemKeyedDataSource<Navigation> {
        return NavigationDataSource(httpManager)
    }
}