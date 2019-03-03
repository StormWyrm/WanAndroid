package com.github.StormWyrm.wanandroid.ui.detail.article

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.Menu
import android.view.MenuItem
import android.webkit.*
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.activity.BaseActivity
import com.github.StormWyrm.wanandroid.base.activity.BaseTitleLoadActivity
import com.github.StormWyrm.wanandroid.base.state.STATE_LOADING
import com.github.StormWyrm.wanandroid.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_article_detail.*

class ArticleDetailActivity : BaseTitleLoadActivity() {
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

    override fun getChildLayoutId(): Int = R.layout.activity_article_detail

    override fun initData() {
        super.initData()
        intent.run {
            title = getStringExtra("title")
            link = getStringExtra("link")
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
                showLoading()
            }


            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                showError()
            }

        }
        webview.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (newProgress > 75 && mStateView.curState == STATE_LOADING) {
                    showSuccess()
                }
            }

        }
        webview.loadUrl(link)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_article_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.detail_share -> {
                onShareClick()
            }
            R.id.detail_copy_link -> {
                onCopyLinkClick()
            }
            R.id.detail_open_browser -> {
                onOpenBrowserClick()
            }
        }
        return true
    }

    override fun onRetry() {
        showLoading()
        webview.reload()
    }

    private fun onOpenBrowserClick() {
        val intent = Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(link)

        }
        intent.resolveActivity(packageManager)?.let {
            startActivity(Intent.createChooser(intent, mActivity.getString(R.string.detail_select_browser)))
        }
    }

    private fun onCopyLinkClick() {
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.primaryClip = ClipData.newPlainText("Label", link)
        ToastUtil.showToast(mContext, R.string.detail_copy_success)
    }

    private fun onShareClick() {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, link)
        }
        intent.resolveActivity(packageManager)?.let {
            startActivity(Intent.createChooser(intent, mActivity.getString(R.string.detail_select_share)))
        }
    }
}
