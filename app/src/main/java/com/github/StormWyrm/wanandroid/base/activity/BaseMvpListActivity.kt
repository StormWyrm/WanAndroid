package com.github.StormWyrm.wanandroid.base.activity

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.mvp.IListView
import com.github.StormWyrm.wanandroid.base.mvp.ITopPresenter
import com.github.StormWyrm.wanandroid.base.mvp.ITopView
import com.github.StormWyrm.wanandroid.base.state.IStateView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.layout_list.*

abstract class BaseMvpListActivity<V : ITopView, P : ITopPresenter> : BaseActivity(), IListView<P> {
    override val mStateView: IStateView by lazy { list_sv }
    override val mRecyclerView: RecyclerView by lazy { list_rv }
    override val mRefreshLayout: SmartRefreshLayout by lazy { refreshLayout }

    open val recyclerViewBgColor = R.color.white
    open val isEnableRefresh = false
    open val isEnableLoadmore = true

    override fun getLayoutId(): Int = R.layout.layout_list

    override fun initView() {
        super.initView()
        list_rv?.let {
            it.setBackgroundColor(recyclerViewBgColor)
        }
        list_sv.onRetry = {
            onRetry()
        }
        refreshLayout.isEnableRefresh = isEnableRefresh
        refreshLayout.isEnableLoadMore = isEnableLoadmore
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun getContext(): Context? = mActivity

    override fun finishActivity() = finish()

    override fun showSuccess() {
        mStateView.showSuccess()
    }

    override fun loadDataError() {
        list_sv.showError()
    }

    override fun noData() {
        list_sv.showEmpty()
    }

    override fun loadMoreError() {
        mRefreshLayout.finishLoadMore(false)
    }

    override fun noMoreData() {
        mRefreshLayout.setNoMoreData(true)
    }

    abstract fun onRetry()
}