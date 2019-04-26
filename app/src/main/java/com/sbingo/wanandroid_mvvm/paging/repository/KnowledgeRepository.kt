package com.sbingo.wanandroid_mvvm.paging.repository

import com.sbingo.wanandroid_mvvm.base.paging.BaseDataSourceFactory
import com.sbingo.wanandroid_mvvm.base.paging.BasePagingRepository
import com.sbingo.wanandroid_mvvm.data.http.HttpManager
import com.sbingo.wanandroid_mvvm.model.Article
import com.sbingo.wanandroid_mvvm.paging.factory.KnowledgeDataSourceFactory

/**
 * Author: Sbingo666
 * Date:   2019/4/26
 */
class KnowledgeRepository(private val httpManager: HttpManager, private val knowledgeId: Int) : BasePagingRepository<Article>() {

    override fun createDataBaseFactory(): BaseDataSourceFactory<Article> {
        return KnowledgeDataSourceFactory(httpManager, knowledgeId)
    }
}