package com.sbingo.wanandroid_mvvm.viewmodel

import androidx.lifecycle.ViewModel
import com.sbingo.wanandroid_mvvm.repository.ProjectRepository

/**
 * Author: Sbingo666
 * Date:   2019/4/23
 */
class ProjectViewModel(private val repository: ProjectRepository) : ViewModel() {

    val projects = repository.getProjects()

}