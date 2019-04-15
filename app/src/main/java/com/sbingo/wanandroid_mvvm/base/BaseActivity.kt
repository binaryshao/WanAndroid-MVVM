package com.sbingo.wanandroid_mvvm.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Author: Sbingo666
 * Date:   2019/4/3
 */
abstract class BaseActivity : AppCompatActivity() {

    protected abstract var layoutId: Int

    protected abstract fun initData()

    protected abstract fun subscribeUi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (this !is BaseBindingActivity<*>) {
            setContentView(layoutId)
        }
    }

    override fun onStart() {
        super.onStart()
        initData()
        subscribeUi()
    }
}