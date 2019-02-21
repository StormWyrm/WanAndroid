package com.github.StormWyrm.wanandroid.ui.detail

import android.content.Intent
import android.graphics.Bitmap
import android.webkit.*
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.activity.BaseActivity
import com.github.StormWyrm.wanandroid.base.state.STATE_SUCCESS
import kotlinx.android.synthetic.main.activity_article_detail.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class ArticleDetailActivity : BaseActivity() {
    private lateinit var title: String
    private lateinit var link: String

    companion object {
        fun start(context: BaseActivity, title: String, link: String) {
            val intent = Intent().apply {
                setClass(context, ArticleDetailActivity::class.java)
                putExtra("title", title)
                putExtra("link", link)
            }
            context.startActivity(intent)
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_article_detail

    override fun initData() {
        super.initData()
        intent.run {
            title = getStringExtra("title")
            link = getStringExtra("link")
        }

        stateView.onRetry = {
            stateView.showLoading()
            webview.reload()
        }
    }

    override fun initView() {
        super.initView()
        initToolbar(title)

        webview.settings.run {
            javaScriptEnabled = true
            cacheMode = WebSettings.LOAD_NO_CACHE
            setSupportZoom(true)
            displayZoomControls = false
        }
        webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                webview.loadUrl(url)
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                stateView.showLoading()
            }


            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                stateView.showError()
            }

        }
        webview.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (newProgress > 75 && stateView.curState != STATE_SUCCESS) {
                    stateView.showSuccess()
                }
            }

        }
        webview.loadUrl(link)
    }

    private fun initToolbar(titleStr: String) {
        toolbar.run {
            title = titleStr
            setSupportActionBar(this)
            setNavigationOnClickListener {
                finish()
            }
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }


}
