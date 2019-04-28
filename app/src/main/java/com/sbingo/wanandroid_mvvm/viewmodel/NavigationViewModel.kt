package com.sbingo.wanandroid_mvvm.viewmodel

import com.sbingo.wanandroid_mvvm.base.paging.BasePagingViewModel
import com.sbingo.wanandroid_mvvm.model.Navigation
import com.sbingo.wanandroid_mvvm.paging.repository.NavigationRepository

/**
 * Author: Sbingo666
 * Date:   2019/4/28
 */
class NavigationViewModel(repository: NavigationRepository) :BasePagingViewModel<Navigation>(repository){

}