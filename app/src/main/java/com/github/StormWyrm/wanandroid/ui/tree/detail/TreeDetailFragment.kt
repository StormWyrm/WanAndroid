package com.github.StormWyrm.wanandroid.ui.tree.detail

import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.fragment.BaseMvpListFragment
import com.github.StormWyrm.wanandroid.bean.tree.detail.TreeDetailDataItem
import com.github.StormWyrm.wanandroid.ui.detail.article.ArticleDetailActivity
import com.github.StormWyrm.wanandroid.ui.detail.search.SearchDetailActivity
import com.github.StormWyrm.wanandroid.ui.tree.adapter.TreeDetailAdapter

class TreeDetailFragment : BaseMvpListFragment<TreeDetailContract.View, TreeDetailContract.Presenter>(),
    TreeDetailContract.View {
    override var mPresenter: TreeDetailContract.Presenter = TreeDetailPresenter()

    private val cid: Int by lazy {
        arguments?.getInt("cid") ?: -1
    }

    private var pageNum: Int = 0
    lateinit var mAdapter: TreeDetailAdapter

    companion object {

        fun newInstance(cid: Int): TreeDetailFragment {
            val bundle = Bundle().apply {
                putInt("cid", cid)
            }
            return TreeDetailFragment().apply {
                arguments = bundle
            }
        }
    }

    override fun initView() {
        super.initView()
        mAdapter = TreeDetailAdapter().apply {
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
                }
            }
        }
        mRecyclerView.run {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mAdapter
        }
    }

    override fun initListener() {
        super.initListener()
        mRefreshLayout.setOnLoadMoreListener {
            mPresenter.requestTreeDetailList(pageNum, cid)
        }
    }

    override fun initLoad() {
        super.initLoad()
        onRetry()
    }

    override fun onRetry() {
        mStateView.showLoading()
        mPresenter.requestTreeDetailList(pageNum, cid)
    }

    override fun onRequestTreeDetailListSuccess(datas: List<TreeDetailDataItem>) {
        if (pageNum++ == 0) {
            mStateView.showSuccess()
            mAdapter.setNewData(datas)
        } else {
            mRefreshLayout.finishLoadMore()
            mAdapter.addData(datas)
        }
    }

}
