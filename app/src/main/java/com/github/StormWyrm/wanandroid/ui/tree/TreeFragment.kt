package com.github.StormWyrm.wanandroid.ui.tree

import androidx.recyclerview.widget.LinearLayoutManager
import com.github.StormWyrm.wanandroid.base.fragment.BaseMvpListFragment
import com.github.StormWyrm.wanandroid.bean.tree.TreeBean
import com.github.StormWyrm.wanandroid.ui.tree.adapter.TreeAdapter

class TreeFragment : BaseMvpListFragment<TreeContract.View, TreeContract.Presenter>(), TreeContract.View {
    override var mPresenter: TreeContract.Presenter = TreePresenter()
    override val isEnableLoadmore: Boolean = false

    private lateinit var mAdapter: TreeAdapter

    companion object {
        fun newInstance() = TreeFragment()
    }

    override fun initView() {
        super.initView()
        mAdapter = TreeAdapter().apply {
            setOnItemClickListener { adapter, view, position ->

            }
        }
        mRecyclerView.run {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mAdapter
        }
    }

    override fun initLoad() {
        super.initLoad()
        onRetry()
    }

    override fun onRetry() {
        mStateView.showLoading()
        mPresenter.requestTreeList()
    }

    override fun onRequestTreeListSuccess(datas: List<TreeBean>) {
        mAdapter.setNewData(datas)
    }
}
