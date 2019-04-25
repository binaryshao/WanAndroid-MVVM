package com.sbingo.wanandroid_mvvm.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

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
        initData()
        subscribeUi()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    protected fun <T> handleData(liveData: LiveData<RequestState<T>>, action: (T) -> Unit) =
        liveData.observe(this, Observer { result ->
            if (result.isLoading()) {
                showLoading()
            } else if (result?.data != null && result.isSuccess()) {
                finishLoading()
                action(result.data)
            } else {
                finishLoading()
            }
        })

    fun showLoading() {
    }

    fun finishLoading() {
    }
}