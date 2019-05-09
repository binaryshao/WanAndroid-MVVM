package com.sbingo.wanandroid_mvvm.viewmodel

import com.sbingo.wanandroid_mvvm.base.paging.BasePagingViewModel
import com.sbingo.wanandroid_mvvm.model.Article
import com.sbingo.wanandroid_mvvm.paging.repository.HomeRepository

/**
 * Author: Sbingo666
 * Date:   2019/4/18
 */
class HomeViewModel(repository: HomeRepository) : BasePagingViewModel<Article>(repository) {

}