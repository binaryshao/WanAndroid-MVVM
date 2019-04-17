package com.sbingo.wanandroid_mvvm.utils

import com.sbingo.wanandroid_mvvm.data.http.HttpManager
import com.sbingo.wanandroid_mvvm.repository.MainRepository
import com.sbingo.wanandroid_mvvm.viewmodel.MainViewModelFactory

/**
 * Author: Sbingo666
 * Date:   2019/4/4
 */
object InjectorUtils {

    private fun getMainRepository(): MainRepository {
        return MainRepository.getInstance(HttpManager.getInstance())
    }

    fun provideMainViewModelFactory(): MainViewModelFactory {
        return MainViewModelFactory(getMainRepository())
    }

}
