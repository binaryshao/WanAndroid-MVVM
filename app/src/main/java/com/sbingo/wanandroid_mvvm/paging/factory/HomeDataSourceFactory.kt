package com.sbingo.wanandroid_mvvm.paging.factory

import com.sbingo.wanandroid_mvvm.base.paging.BaseDataSourceFactory
import com.sbingo.wanandroid_mvvm.base.paging.BaseItemKeyedDataSource
import com.sbingo.wanandroid_mvvm.data.http.HttpManager
import com.sbingo.wanandroid_mvvm.model.Article
import com.sbingo.wanandroid_mvvm.paging.source.HomeDataSource

/**
 * Author: Sbingo666
 * Date:   2019/4/17
 */
class HomeDataSourceFactory(private val httpManager: HttpManager) : BaseDataSourceFactory<Article>() {

    override fun createDataSource(): BaseItemKeyedDataSource<Article> {
        return HomeDataSource(httpManager)
    }
}