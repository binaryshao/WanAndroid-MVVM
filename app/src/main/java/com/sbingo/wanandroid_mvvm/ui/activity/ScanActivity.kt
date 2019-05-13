package com.sbingo.wanandroid_mvvm.ui.activity

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import com.sbingo.wanandroid_mvvm.R
import com.sbingo.wanandroid_mvvm.base.BaseActivity
import com.sbingo.wanandroid_mvvm.utils.Listeners
import me.dm7.barcodescanner.zbar.ZBarScannerView

/**
 * Author: Sbingo666
 * Date:   2019/5/10
 */

class ScanActivity : BaseActivity(), ZBarScannerView.ResultHandler {

    private lateinit var context: Context

    override var layoutId = 0

    override fun initData() {
    }

    override fun subscribeUi() {
    }

    private lateinit var mScannerView: ZBarScannerView

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mScannerView = ZBarScannerView(this)
        setContentView(mScannerView)
        context = this
        checkPermissions(arrayOf(Manifest.permission.CAMERA),
            arrayOf("摄像头"),
            arrayOf(getString(R.string.camera_permission_reason)),
            object : Listeners.PermissionListener {
                override fun onGranted() {
                    mScannerView.setResultHandler(context as ScanActivity)
                    mScannerView.startCamera()
                }

                override fun onDenied(permissions: List<String>) {
                    finish()
                }

                override fun onShowReason() {
                }
            })
    }

    public override fun onPause() {
        super.onPause()
        mScannerView.stopCamera()
    }

    override fun handleResult(result: me.dm7.barcodescanner.zbar.Result) {
        AlertDialog.Builder(this)
            .setTitle(R.string.scan_result)
            .setMessage(result.contents)
            .setCancelable(false)
            .setPositiveButton(R.string.i_know, null)
            .show()
    }
}
