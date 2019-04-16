package com.sbingo.wanandroid_mvvm.ui.fragment

import com.sbingo.wanandroid_mvvm.R
import com.sbingo.wanandroid_mvvm.base.BaseFragment
import kotlinx.android.synthetic.main.refresh_layout.*

/**
 * Author: Sbingo666
 * Date:   2019/4/15
 */
class NavigationFragment : BaseFragment() {

    override var layoutId = R.layout.refresh_layout


    override fun initData() {
        test.text = getString(R.string.navigation)
    }

    override fun subscribeUi() {
    }
}