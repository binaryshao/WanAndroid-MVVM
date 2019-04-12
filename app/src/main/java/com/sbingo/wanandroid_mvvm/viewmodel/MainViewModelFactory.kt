package com.sbingo.wanandroid_mvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sbingo.wanandroid_mvvm.repository.MainRepository


/**
 * Author: Sbingo666
 * Date:   2019/4/4
 */
class MainViewModelFactory(val repository: MainRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = MainViewModel(repository) as T
}
