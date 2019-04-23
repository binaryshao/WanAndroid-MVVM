package com.sbingo.wanandroid_mvvm.paging.repository

import com.sbingo.wanandroid_mvvm.base.paging.BaseDataSourceFactory
import com.sbingo.wanandroid_mvvm.base.paging.BasePagingRepository
import com.sbingo.wanandroid_mvvm.data.http.HttpManager
import com.sbingo.wanandroid_mvvm.model.Article
import com.sbingo.wanandroid_mvvm.paging.factory.ProjectDataSourceFactory

/**
 * Author: Sbingo666
 * Date:   2019/4/23
 */
class ProjectArticleRepository(private val httpManager: HttpManager, private val projectId: Int) : BasePagingRepository<Article>() {

    override fun createDataBaseFactory(): BaseDataSourceFactory<Article> {
        return ProjectDataSourceFactory(httpManager, projectId)
    }
}