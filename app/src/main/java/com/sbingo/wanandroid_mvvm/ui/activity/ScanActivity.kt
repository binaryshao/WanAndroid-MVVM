package com.sbingo.wanandroid_mvvm.ui.activity

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import com.sbingo.wanandroid_mvvm.R
import me.dm7.barcodescanner.zbar.ZBarScannerView

/**
 * Author: Sbingo666
 * Date:   2019/5/10
 */

class ScanActivity : Activity(), ZBarScannerView.ResultHandler {

    private lateinit var mScannerView: ZBarScannerView

    public override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        mScannerView = ZBarScannerView(this)
        setContentView(mScannerView)
    }

    public override fun onResume() {
        super.onResume()
        mScannerView.setResultHandler(this)
        mScannerView.startCamera()
    }

    public override fun onPause() {
        super.onPause()
        mScannerView.stopCamera()
    }

    override fun handleResult(result: me.dm7.barcodescanner.zbar.Result) {
        AlertDialog.Builder(this)
            .setTitle(R.string.scan_result)
            .setMessage(result.contents)
            .setPositiveButton(R.string.i_know, null)
            .show()
    }
}
