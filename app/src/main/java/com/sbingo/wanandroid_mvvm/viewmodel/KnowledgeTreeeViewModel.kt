package com.sbingo.wanandroid_mvvm.viewmodel

import com.sbingo.wanandroid_mvvm.base.paging.BasePagingViewModel
import com.sbingo.wanandroid_mvvm.model.Chapter
import com.sbingo.wanandroid_mvvm.paging.repository.KnowledgeTreeRepository

/**
 * Author: Sbingo666
 * Date:   2019/4/26
 */
class KnowledgeTreeeViewModel(repository: KnowledgeTreeRepository) :BasePagingViewModel<Chapter>(repository){

}