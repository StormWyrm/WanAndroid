package com.github.StormWyrm.wanandroid.base.fragment

import androidx.recyclerview.widget.RecyclerView
import com.exmple.corelib.state.IStateView
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.mvp.IListView
import com.github.StormWyrm.wanandroid.base.mvp.ITopPresenter
import com.github.StormWyrm.wanandroid.base.mvp.ITopView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.layout_list.*

abstract class BaseMvpListFragment<V : ITopView, P : ITopPresenter> : BaseMvpFragment<V, P>(), IListView<P> {
    override val mStateView: IStateView by lazy { list_sv }
    override val mRecyclerView: RecyclerView by lazy { list_rv }
    override val mRefreshLayout: SmartRefreshLayout by lazy { refreshLayout }

    open val recyclerViewBgColor = R.color.white
    open val isEnableRefresh = false
    open val isEnableLoadmore = true

    override fun getLayoutId(): Int = R.layout.layout_list

    override fun initView() {
        super.initView()
//        list_rv?.let {
//            it.setBackgroundColor(recyclerViewBgColor)
//        }
        list_sv.onRetry = {
            onRetry()
        }
        refreshLayout.isEnableRefresh = isEnableRefresh
        refreshLayout.isEnableLoadMore = isEnableLoadmore
    }

    override fun loadDataError() {
        list_sv.showError()
    }

    override fun noData() {
        list_sv.showEmpty()
    }

    override fun loadMoreError() {
        refreshLayout.finishLoadMore(false)
    }

    override fun noMoreData() {
        refreshLayout.setNoMoreData(true)
    }

    abstract fun onRetry()

}