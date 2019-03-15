package com.github.StormWyrm.wanandroid.ui.detail.search

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.activity.BaseActivity
import com.github.StormWyrm.wanandroid.base.activity.BaseMvpListActivity
import com.github.StormWyrm.wanandroid.bean.query.QueryDataItem
import com.github.StormWyrm.wanandroid.ui.detail.article.ArticleDetailActivity
import com.github.StormWyrm.wanandroid.ui.detail.search.adapter.SearchDetailAdapter

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
    private lateinit var mSearchQueryAdapter: SearchDetailAdapter

    override fun getLayoutId(): Int {
        return R.layout.activity_search_detail
    }

    override fun initView() {
        super.initView()
        initToolbar(queryKey)

        mSearchQueryAdapter = SearchDetailAdapter(null).apply {
            setOnItemClickListener { _, _, position ->
                getItem(position)?.apply {
                    ArticleDetailActivity.start(mActivity, title, link)
                }
            }
        }

        mRecyclerView.run {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mSearchQueryAdapter
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
            mSearchQueryAdapter.setNewData(data)
        } else {
            mRefreshLayout.finishLoadMore()
            mSearchQueryAdapter.addData(data)
        }
    }

    companion object {
        const val KEY = 0
        const val AUTHOR = 1

        fun start(context: BaseActivity, queryKey: String,catecory : Int) {
            val intent = Intent().apply {
                setClass(context, SearchDetailActivity::class.java)
                putExtra("queryKey", queryKey)
                putExtra("catecory",catecory)
            }
            context.startActivity(intent)
        }
    }
}