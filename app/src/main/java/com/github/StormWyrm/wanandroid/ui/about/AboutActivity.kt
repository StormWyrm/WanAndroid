package com.github.StormWyrm.wanandroid.ui.about

import android.content.Intent
import android.view.View
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.activity.BaseActivity
import com.github.StormWyrm.wanandroid.ui.detail.article.ArticleDetailActivity

class AboutActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_about
    }

    override fun initView() {
        super.initView()
        initToolbar(getString(R.string.main_about_title))
    }

    fun onGithubClick(view : View){
        ArticleDetailActivity.start(mActivity, "StormWyrm", "https://github.com/StormWyrm")

    }
    companion object {
        fun start(context: BaseActivity) {
            val intent = Intent().apply {
                setClass(context, AboutActivity::class.java)
            }
            context.startActivity(intent)
        }
    }
}