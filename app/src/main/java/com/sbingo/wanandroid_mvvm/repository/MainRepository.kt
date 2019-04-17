package com.sbingo.wanandroid_mvvm.repository

import com.sbingo.wanandroid_mvvm.data.http.HttpManager

/**
 * Author: Sbingo666
 * Date:   2019/4/4
 */
class MainRepository(val httpManager: HttpManager) {

    companion object {
        @Volatile
        private var instance: MainRepository? = null

        fun getInstance(httpManager: HttpManager) =
            instance ?: synchronized(this) {
                instance ?: MainRepository(httpManager).also { instance = it }
            }
    }

}
