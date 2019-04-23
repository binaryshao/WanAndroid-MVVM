package com.sbingo.wanandroid_mvvm.paging.repository

import com.sbingo.wanandroid_mvvm.base.paging.BaseDataSourceFactory
import com.sbingo.wanandroid_mvvm.base.paging.BasePagingRepository
import com.sbingo.wanandroid_mvvm.data.http.HttpManager
import com.sbingo.wanandroid_mvvm.model.Article
import com.sbingo.wanandroid_mvvm.paging.factory.WXDataSourceFactory

/**
 * Author: Sbingo666
 * Date:   2019/4/23
 */
class WXRepository(private val httpManager: HttpManager, private val wxId: Int) : BasePagingRepository<Article>() {

    override fun createDataBaseFactory(): BaseDataSourceFactory<Article> {
        return WXDataSourceFactory(httpManager, wxId)
    }
}