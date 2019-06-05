package com.sbingo.wanandroid_mvvm.ui.activity

import android.os.Build
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.NestedScrollAgentWebView
import com.sbingo.wanandroid_mvvm.Constants
import com.sbingo.wanandroid_mvvm.R
import com.sbingo.wanandroid_mvvm.base.BaseActivity
import kotlinx.android.synthetic.main.activity_web.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Author: Sbingo666
 * Date:   2019/4/25
 */
class WebActivity : BaseActivity() {

    private var agentWeb: AgentWeb? = null
    private lateinit var webTitle: String
    private lateinit var webUrl: String
    private lateinit var errorMsg: TextView
    private val mWebView: NestedScrollAgentWebView by lazy {
        NestedScrollAgentWebView(this)
    }

    override var layoutId = R.layout.activity_web

    override fun initData() {
        toolbar.apply {
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        iv_close.apply {
            visibility = View.VISIBLE
            setOnClickListener { finish() }
        }
        tv_title.apply {
            visibility = View.VISIBLE
            text = getString(R.string.loading)
            isSelected = true
        }
        intent.extras?.apply {
            webTitle = getString(Constants.WEB_TITLE, getString(R.string.title_err))
            webUrl = getString(Constants.WEB_URL, "")
        }
        initWebView()
    }

    override fun subscribeUi() {
    }

    private fun initWebView() {
        val layoutParams =
            CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        layoutParams.behavior = AppBarLayout.ScrollingViewBehavior()
        val errorView = layoutInflater.inflate(R.layout.web_error_page, null)
        errorMsg = errorView.findViewById(R.id.error_msg)
        errorMsg.text = getString(R.string.web_page_err, webUrl)

        agentWeb = AgentWeb.with(this)
            .setAgentWebParent(web_container, 1, layoutParams)
            .useDefaultIndicator()
            .setWebView(mWebView)
            .setWebChromeClient(webChromeClient)
            .setMainFrameErrorView(errorView)
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
            .createAgentWeb()
            .ready()
            .go(webUrl)

        agentWeb?.webCreator?.webView?.run {
            settings.domStorageEnabled = true
            webViewClient = mWebViewClient
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
        }
    }

    override fun onBackPressed() {
        agentWeb?.run {
            if (!back()) {
                super.onBackPressed()
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (agentWeb?.handleKeyEvent(keyCode, event)!!) {
            true
        } else {
            finish()
            super.onKeyDown(keyCode, event)
        }
    }

    override fun onResume() {
        agentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onPause() {
        agentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        agentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
    }

    private val webChromeClient = object : com.just.agentweb.WebChromeClient() {
        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            tv_title.text = title
        }
    }

    private val mWebViewClient = object : com.just.agentweb.WebViewClient() {
    }

}