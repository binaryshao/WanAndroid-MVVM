package com.sbingo.wanandroid_mvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * Author: Sbingo666
 * Date:   2019/4/15
 */
abstract class BaseFragment : Fragment() {

    protected abstract var layoutId: Int

    protected abstract fun initData()

    protected abstract fun subscribeUi()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        subscribeUi()
    }
}