package com.sbingo.wanandroid_mvvm.viewmodel

import androidx.lifecycle.ViewModel
import com.sbingo.wanandroid_mvvm.repository.MainRepository

/**
 * Author: Sbingo666
 * Date:   2019/4/4
 */
class MainViewModel(repository: MainRepository) : ViewModel() {

    var curPage: Int = 0
    var articlePages = repository.getArticles()
}
