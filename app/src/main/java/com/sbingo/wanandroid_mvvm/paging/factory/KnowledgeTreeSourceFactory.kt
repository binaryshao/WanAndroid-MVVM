package com.sbingo.wanandroid_mvvm.paging.factory

import com.sbingo.wanandroid_mvvm.base.paging.BaseDataSourceFactory
import com.sbingo.wanandroid_mvvm.base.paging.BaseItemKeyedDataSource
import com.sbingo.wanandroid_mvvm.data.http.HttpManager
import com.sbingo.wanandroid_mvvm.model.Chapter
import com.sbingo.wanandroid_mvvm.paging.source.KnowledgeTreeDataSource

/**
 * Author: Sbingo666
 * Date:   2019/4/26
 */
class KnowledgeTreeSourceFactory(private val httpManager: HttpManager) :
    BaseDataSourceFactory<Chapter>() {

    override fun createDataSource(): BaseItemKeyedDataSource<Chapter> {
        return KnowledgeTreeDataSource(httpManager)
    }
}