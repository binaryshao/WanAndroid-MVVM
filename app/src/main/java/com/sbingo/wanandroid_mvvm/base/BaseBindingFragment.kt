package com.sbingo.wanandroid_mvvm.base

import androidx.databinding.ViewDataBinding

/**
 * Author: Sbingo666
 * Date:   2019/4/15
 */
abstract class BaseBindingFragment<T : ViewDataBinding> : BaseFragment() {

    override var layoutId = 0

    protected abstract var binding: T

}