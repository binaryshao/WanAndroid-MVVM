package com.sbingo.wanandroid_mvvm.base.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

/**
 * Author: Sbingo666
 * Date:   2019/4/12
 */
abstract class BaseDataSourceFactory<T> : DataSource.Factory<Int,T>() {

    val sourceLivaData = MutableLiveData<BaseItemKeyedDataSource<T>>()

    override fun create(): BaseItemKeyedDataSource<T> {
        val dataSource: BaseItemKeyedDataSource<T> = createDataSource()
        sourceLivaData.postValue(dataSource)
        return dataSource
    }

    abstract fun createDataSource(): BaseItemKeyedDataSource<T>

}