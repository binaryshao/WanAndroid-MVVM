package com.sbingo.wanandroid_mvvm.paging.repository

import com.sbingo.wanandroid_mvvm.base.paging.BaseDataSourceFactory
import com.sbingo.wanandroid_mvvm.base.paging.BasePagingRepository
import com.sbingo.wanandroid_mvvm.data.http.HttpManager
import com.sbingo.wanandroid_mvvm.model.Chapter
import com.sbingo.wanandroid_mvvm.paging.factory.KnowledgeTreeSourceFactory

/**
 * Author: Sbingo666
 * Date:   2019/4/26
 */
class KnowledgeTreeRepository(private val httpManager: HttpManager) : BasePagingRepository<Chapter>() {

    override fun createDataBaseFactory(): BaseDataSourceFactory<Chapter> {
        return KnowledgeTreeSourceFactory(httpManager)
    }
}