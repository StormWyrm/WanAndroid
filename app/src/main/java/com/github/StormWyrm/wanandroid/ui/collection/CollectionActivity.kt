package com.github.StormWyrm.wanandroid.ui.collection

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.activity.BaseActivity
import com.github.StormWyrm.wanandroid.base.activity.BaseMvpListActivity
import com.github.StormWyrm.wanandroid.bean.article.ArticleBean
import com.github.StormWyrm.wanandroid.bean.article.ArticleDataItem
import com.github.StormWyrm.wanandroid.ui.collection.adapter.CollectionAdapter
import com.github.StormWyrm.wanandroid.ui.detail.article.ArticleDetailActivity
import com.github.StormWyrm.wanandroid.ui.detail.search.SearchDetailActivity
import com.github.StormWyrm.wanandroid.utils.ToastUtil

class CollectionActivity : BaseMvpListActivity<CollectionContract.View, CollectionContract.Presenter>(),
    CollectionContract.View {
    override var mPresenter: CollectionContract.Presenter = CollectionPresenter()

    private var pageNum: Int = 0
    private lateinit var mAdapter: CollectionAdapter

    override fun getLayoutId(): Int {
        return R.layout.activity_collection
    }

    override fun initView() {
        super.initView()
        initToolbar(getString(R.string.main_collect_title))
        mAdapter = CollectionAdapter().apply {
            setOnItemClickListener { _, _, position ->
                getItem(position)?.run {
                    ArticleDetailActivity.start(mActivity, title, link)
                }
            }
            setOnItemChildClickListener { _, view, position ->
                when (view.id) {
                    R.id.tvAuthor -> {
                        getItem(position)?.run {
                            SearchDetailActivity.start(mActivity, author, SearchDetailActivity.AUTHOR)
                        }
                    }

                    R.id.ivStar -> {
                        getItem(position)?.run {
                            onStarClick(this, position)
                        }
                    }
                }
            }
        }
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mRecyclerView.adapter = mAdapter

        onRetry()
    }

    override fun initLisenter() {
        super.initLisenter()
        mRefreshLayout.setOnLoadMoreListener {
            mPresenter.requestCollectionList(pageNum)
        }
    }

    override fun onRetry() {
        mStateView.showLoading()
        mPresenter.requestCollectionList(pageNum)
    }

    override fun onRequestCollectionListSuccess(articleList: ArticleBean) {
        if (pageNum++ == 0) {
            mStateView.showSuccess()
            mAdapter.setNewData(articleList.datas)
        } else {
            mAdapter.addData(articleList.datas)
            mRefreshLayout.finishLoadMore(true)
        }
    }

    override fun onRemoveMyCollectionSuccess(position: Int) {
        mAdapter.remove(position)
        if (mAdapter.data.isNullOrEmpty()) {
            mStateView.showEmpty()
        }
        ToastUtil.showToast(mActivity, R.string.uncollect_success)
    }

    private fun onStarClick(dataItem: ArticleDataItem, position: Int) {
        dataItem.run {
            mPresenter.requestRemoveMyCollection(id, originId, position)
        }
    }

    companion object {
        fun start(context: BaseActivity) {
            val intent = Intent().apply {
                setClass(context, CollectionActivity::class.java)
            }
            context.startActivity(intent)
        }
    }
}