package com.sbingo.wanandroid_mvvm.base.paging

import androidx.lifecycle.Transformations
import androidx.paging.Config
import androidx.paging.toLiveData

/**
 * Author: Sbingo666
 * Date:   2019/4/12
 */
abstract class BasePagingRepository<T> {

    fun getData(pageSize: Int): Listing<T> {

        val sourceFactory = createDataBaseFactory()
        val pagedList = sourceFactory.toLiveData(
            config = Config(
                pageSize = pageSize,
                enablePlaceholders = false,
                initialLoadSizeHint = pageSize * 2
            )
        )
        val refreshState = Transformations.switchMap(sourceFactory.sourceLivaData) { it.refreshStatus }
        val networkStatus = Transformations.switchMap(sourceFactory.sourceLivaData) { it.loadMoreStatus }

        return Listing(
            pagedList,
            networkStatus,
            refreshState,
            refresh = {
                sourceFactory.sourceLivaData.value?.invalidate()
            },
            retry = {
                sourceFactory.sourceLivaData.value?.retryFailed()
            }
        )
    }

    abstract fun createDataBaseFactory(): BaseDataSourceFactory<T>
}