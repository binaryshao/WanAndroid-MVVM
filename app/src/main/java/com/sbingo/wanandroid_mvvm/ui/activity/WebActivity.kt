package com.sbingo.wanandroid_mvvm.ui.activity

import android.os.Build
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
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
    private val mWebView: NestedScrollAgentWebView by lazy {
        NestedScrollAgentWebView(this)
    }

    override var layoutId = R.layout.activity_web

    override fun initData() {
        toolbar.apply {
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        tv_title.apply {
            visibility = View.VISIBLE
            text = getString(R.string.loading)
            isSelected = true
        }
        intent.extras?.let {
            webTitle = it.getString(Constants.WEB_TITLE, getString(R.string.title_err))
            webUrl = it.getString(Constants.WEB_URL, "")
        }
        initWebView()
    }

    override fun subscribeUi() {
    }

    private fun initWebView() {
        val layoutParams =
            CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        layoutParams.behavior = AppBarLayout.ScrollingViewBehavior()

        agentWeb = AgentWeb.with(this)
            .setAgentWebParent(web_container, 1, layoutParams)
            .useDefaultIndicator()
            .setWebView(mWebView)
            .setWebChromeClient(webChromeClient)
            .setMainFrameErrorView(R.layout.web_error_page, -1)
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
            .createAgentWeb()
            .ready()
            .go(webUrl)

        agentWeb?.webCreator?.webView?.run {
            settings.domStorageEnabled = true
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

}