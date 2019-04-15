package com.sbingo.wanandroid_mvvm.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding

/**
 * Author: Sbingo666
 * Date:   2019/4/15
 */
abstract class BaseBindingFragment<T : ViewDataBinding> : BaseFragment() {

    override var layoutId = 0

    protected abstract fun initBinding(): T

    lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = initBinding()
    }
}