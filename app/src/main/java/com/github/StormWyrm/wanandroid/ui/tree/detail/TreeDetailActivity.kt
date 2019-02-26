package com.github.StormWyrm.wanandroid.ui.tree.detail

import android.content.Context
import android.content.Intent
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.activity.BaseActivity
import com.github.StormWyrm.wanandroid.bean.tree.TreeBean
import com.github.StormWyrm.wanandroid.ui.tree.adapter.TreeDetailFragmentAdpter
import kotlinx.android.synthetic.main.activity_tree_detail.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class TreeDetailActivity : BaseActivity() {
    private val treeBean: TreeBean by lazy {
        intent.getParcelableExtra("TreeBean") as TreeBean
    }

    companion object {
        fun start(context: Context, treeBean: TreeBean) {
            val intent = Intent().apply {
                setClass(context, TreeDetailActivity::class.java)
                putExtra("TreeBean", treeBean)
            }
            context.startActivity(intent)
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_tree_detail

    override fun initData() {
        super.initData()
        initToolbar(treeBean.name)
    }

    override fun initView() {
        super.initView()
        vpTreeDetail.adapter = TreeDetailFragmentAdpter(treeBean, supportFragmentManager)
        tabLayout.setupWithViewPager(vpTreeDetail)
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
