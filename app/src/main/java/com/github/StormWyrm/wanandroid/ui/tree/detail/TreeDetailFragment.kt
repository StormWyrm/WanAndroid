package com.github.StormWyrm.wanandroid.ui.tree.detail

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.fragment.BaseMvpListFragment
import com.github.StormWyrm.wanandroid.bean.tree.detail.TreeDetailDataItem
import com.github.StormWyrm.wanandroid.ui.detail.article.ArticleDetailActivity
import com.github.StormWyrm.wanandroid.ui.detail.search.SearchDetailActivity
import com.github.StormWyrm.wanandroid.ui.login.LoginActivity
import com.github.StormWyrm.wanandroid.ui.tree.adapter.TreeDetailAdapter
import com.github.StormWyrm.wanandroid.utils.ToastUtil

class TreeDetailFragment : BaseMvpListFragment<TreeDetailContract.View, TreeDetailContract.Presenter>(),
    TreeDetailContract.View {
    override var mPresenter: TreeDetailContract.Presenter = TreeDetailPresenter()

    private val cid: Int by lazy {
        arguments?.getInt("cid") ?: -1
    }

    private var pageNum: Int = 0
    lateinit var mAdapter: TreeDetailAdapter

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
                    R.id.ivStar -> {
                        getItem(position)?.run {
                            onStarClick(this, position)
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
        fun newInstance(cid: Int): TreeDetailFragment {
            val bundle = Bundle().apply {
                putInt("cid", cid)
            }
            return TreeDetailFragment().apply {
                arguments = bundle
            }
        }
    }
}
