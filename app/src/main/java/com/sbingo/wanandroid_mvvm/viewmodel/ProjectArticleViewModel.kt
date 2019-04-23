package com.sbingo.wanandroid_mvvm.viewmodel

import com.sbingo.wanandroid_mvvm.base.paging.BasePagingViewModel
import com.sbingo.wanandroid_mvvm.model.Article
import com.sbingo.wanandroid_mvvm.paging.repository.ProjectArticleRepository

/**
 * Author: Sbingo666
 * Date:   2019/4/18
 */
class ProjectArticleViewModel(repository: ProjectArticleRepository) :BasePagingViewModel<Article>(repository){

}