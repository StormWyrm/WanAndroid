package com.github.StormWyrm.wanandroid.ui.detail.search

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.activity.BaseActivity
import com.github.StormWyrm.wanandroid.base.activity.BaseMvpListActivity
import com.github.StormWyrm.wanandroid.bean.query.QueryDataItem
import com.github.StormWyrm.wanandroid.bean.tree.detail.TreeDetailDataItem
import com.github.StormWyrm.wanandroid.ui.detail.article.ArticleDetailActivity
import com.github.StormWyrm.wanandroid.ui.detail.search.adapter.SearchDetailAdapter
import com.github.StormWyrm.wanandroid.ui.login.LoginActivity
import com.github.StormWyrm.wanandroid.utils.ToastUtil

class SearchDetailActivity : BaseMvpListActivity<SearchDetailContract.View, SearchDetailContract.Presenter>(),
    SearchDetailContract.View {
    override var mPresenter: SearchDetailContract.Presenter = SearchDetailPresenter()

    private var pageNum: Int = 0
    private val queryKey: String by lazy {
        intent.getStringExtra("queryKey")
    }
    private val category: Int by lazy {
        intent.getIntExtra("catecory", KEY)
    }
    private lateinit var mAdapter: SearchDetailAdapter

    override fun getLayoutId(): Int {
        return R.layout.activity_search_detail
    }

    override fun initView() {
        super.initView()
        initToolbar(queryKey)

        mAdapter = SearchDetailAdapter(null).apply {
            setOnItemClickListener { _, _, position ->
                getItem(position)?.apply {
                    ArticleDetailActivity.start(mActivity, title, link)
                }
            }
        }

        mRecyclerView.run {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mAdapter
        }

        onRetry()
    }

    override fun initLisenter() {
        super.initLisenter()
        mRefreshLayout.setOnLoadMoreListener {
            mPresenter.requestQueryKey(category, queryKey, pageNum)
        }
    }

    override fun onRetry() {
        mStateView.showLoading()
        mPresenter.requestQueryKey(category, queryKey, pageNum)
    }

    override fun onQueryKeySuccess(data: List<QueryDataItem>) {
        if (pageNum++ == 0) {
            showSuccess()
            mAdapter.setNewData(data)
        } else {
            mRefreshLayout.finishLoadMore()
            mAdapter.addData(data)
        }
    }

    override fun onCollectSuccess(position: Int) {
        mAdapter.getItem(position)?.run {
            collect = true
            mAdapter.setData(position, this)
        }
        ToastUtil.showToast(mActivity, R.string.collect_success)
    }

    override fun onUncollectSuccess(position: Int) {
        mAdapter.getItem(position)?.run {
            collect = false
            mAdapter.setData(position, this)
        }
        ToastUtil.showToast(mActivity, R.string.uncollect_success)
    }

    override fun notLogin() {
        AlertDialog.Builder(mActivity)
            .setTitle(R.string.dialog_hit)
            .setMessage(R.string.dialog_login_hint)
            .setPositiveButton(R.string.dialog_ok) { dialogInterface: DialogInterface, i: Int ->
                LoginActivity.start(mActivity)
                dialogInterface.dismiss()
            }
            .setNegativeButton(R.string.dialog_cancel) { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }.show()
    }

    private fun onStarClick(dataItem: TreeDetailDataItem, position: Int) {
        if (dataItem.collect) {
            mPresenter.requestUncollect(dataItem.id, position)
        } else {
            mPresenter.requestCollect(dataItem.id, position)
        }
    }


    companion object {
        const val KEY = 0
        const val AUTHOR = 1

        fun start(context: BaseActivity, queryKey: String, catecory: Int) {
            val intent = Intent().apply {
                setClass(context, SearchDetailActivity::class.java)
                putExtra("queryKey", queryKey)
                putExtra("catecory", catecory)
            }
            context.startActivity(intent)
        }
    }
}