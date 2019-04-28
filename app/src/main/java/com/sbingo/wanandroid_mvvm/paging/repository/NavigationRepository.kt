package com.sbingo.wanandroid_mvvm.paging.repository

import com.sbingo.wanandroid_mvvm.base.paging.BaseDataSourceFactory
import com.sbingo.wanandroid_mvvm.base.paging.BasePagingRepository
import com.sbingo.wanandroid_mvvm.data.http.HttpManager
import com.sbingo.wanandroid_mvvm.model.Navigation
import com.sbingo.wanandroid_mvvm.paging.factory.NavigationDataSourceFactory

/**
 * Author: Sbingo666
 * Date:   2019/4/28
 */
class NavigationRepository(private val httpManager: HttpManager) : BasePagingRepository<Navigation>() {

    override fun createDataBaseFactory(): BaseDataSourceFactory<Navigation> {
        return NavigationDataSourceFactory(httpManager)
    }
}