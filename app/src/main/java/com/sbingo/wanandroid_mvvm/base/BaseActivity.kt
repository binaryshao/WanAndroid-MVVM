package com.sbingo.wanandroid_mvvm.base

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.sbingo.wanandroid_mvvm.Constants
import com.sbingo.wanandroid_mvvm.R
import com.sbingo.wanandroid_mvvm.ui.activity.ScanActivity
import com.sbingo.wanandroid_mvvm.utils.Listeners
import java.util.*

/**
 * Author: Sbingo666
 * Date:   2019/4/3
 */
abstract class BaseActivity : AppCompatActivity() {

    protected abstract var layoutId: Int

    protected abstract fun initData()

    protected abstract fun subscribeUi()

    private lateinit var permissionListener: Listeners.PermissionListener
    private lateinit var deniedPermissions: HashMap<String, Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (this !is BaseBindingActivity<*> && this !is ScanActivity) {
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

    protected fun checkPermissions(permissions: Array<String>, permissionsCN: Array<String>, reasons: Array<String>, listener: Listeners.PermissionListener) {
        permissionListener = listener
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            permissionListener.onGranted()
        } else {
            deniedPermissions = HashMap()
            permissions.forEachIndexed { index, s ->
                if (ContextCompat.checkSelfPermission(this, s) != PermissionChecker.PERMISSION_GRANTED) {
                    deniedPermissions[s] = arrayOf(permissionsCN[index], reasons[index])
                }
            }
            if (deniedPermissions.isEmpty()) {
                permissionListener.onGranted()
            } else {
                deniedPermissions.forEach {
                    if (shouldShowRequestPermissionRationale(it.key)) {
                        //用户拒绝过权限该方法就会返回 true
                        showPermissionDeniedDialog(it.value[0], it.value[1]) {
                            requestPermissions(deniedPermissions.keys.toTypedArray(), Constants.PERMISSION_CODE)
                            permissionListener.onShowReason()
                        }
                        return
                    }
                }
                //选择不再提示再请求后，不会有请求提示，会直接收到拒绝回调
                requestPermissions(deniedPermissions.keys.toTypedArray(), Constants.PERMISSION_CODE)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        when (requestCode) {
            Constants.PERMISSION_CODE -> if (grantResults.isNotEmpty()) {
                val deniedAgainPermissions = ArrayList<String>()
                for (i in grantResults.indices) {
                    val permission = permissions[i]
                    val grantResult = grantResults[i]
                    if (grantResult != PermissionChecker.PERMISSION_GRANTED) {
                        deniedAgainPermissions.add(permission)
                    }
                }
                if (deniedAgainPermissions.isEmpty()) {
                    permissionListener.onGranted()
                } else {
                    deniedAgainPermissions.forEach { now ->
                        deniedPermissions.forEach { old ->
                            if (now == old.key) {
                                showPermissionDeniedDialog(old.value[0], old.value[1]) { permissionListener.onDenied(deniedAgainPermissions) }
                                return
                            }
                        }
                    }
                    permissionListener.onDenied(deniedAgainPermissions)
                }
            }
        }
    }

    private fun showPermissionDeniedDialog(p: String, reason: String, action: () -> Unit) {
        AlertDialog.Builder(this)
            .setTitle(String.format("缺少【%s】权限", p))
            .setMessage(reason)
            .setCancelable(false)
            .setPositiveButton(
                R.string.i_know
            ) { _, _ -> action() }
            .show()
    }
}