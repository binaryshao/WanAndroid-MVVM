package com.sbingo.wanandroid_mvvm.paging.factory

import com.sbingo.wanandroid_mvvm.base.paging.BaseDataSourceFactory
import com.sbingo.wanandroid_mvvm.base.paging.BaseItemKeyedDataSource
import com.sbingo.wanandroid_mvvm.data.http.HttpManager
import com.sbingo.wanandroid_mvvm.model.Article
import com.sbingo.wanandroid_mvvm.paging.source.ProjectDataSource

/**
 * Author: Sbingo666
 * Date:   2019/4/23
 */
class ProjectDataSourceFactory(private val httpManager: HttpManager, private val projectId: Int) :
    BaseDataSourceFactory<Article>() {

    override fun createDataSource(): BaseItemKeyedDataSource<Article> {
        return ProjectDataSource(httpManager, projectId)
    }
}