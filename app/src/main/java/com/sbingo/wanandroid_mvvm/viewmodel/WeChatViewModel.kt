package com.sbingo.wanandroid_mvvm.viewmodel

import androidx.lifecycle.ViewModel
import com.sbingo.wanandroid_mvvm.repository.WeChatRepository

/**
 * Author: Sbingo666
 * Date:   2019/4/22
 */
class WeChatViewModel(private val repository: WeChatRepository) : ViewModel() {

    val wxChapters = repository.getWXChapters()

}