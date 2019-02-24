package com.github.StormWyrm.wanandroid.base.fragment

import android.view.View
import androidx.annotation.LayoutRes
import com.github.StormWyrm.wanandroid.base.mvp.ILoadView
import com.github.StormWyrm.wanandroid.base.mvp.ITopPresenter
import com.github.StormWyrm.wanandroid.base.mvp.ITopView
import com.github.StormWyrm.wanandroid.base.state.IStateView
import kotlinx.android.synthetic.main.fragment_base_load.*

abstract class BaseMvpLoadFragment<V : ITopView, P : ITopPresenter> : BaseMvpFragment<V, P>(), ILoadView<P> {
    override val mStateView: IStateView by lazy { sv }

    override fun initView() {
        super.initView()
        val realView = View.inflate(mContext, getChildLayoutId(), null)
        fl_container.addView(realView)
        sv.onRetry = {
            onRetry()
        }
    }

    override fun showLoading() {
        sv.showLoading()
    }
    override fun showSuccess() {
        sv.showSuccess()
    }

    override fun showError() {
        sv.showError()
    }

    override fun showEmpty() {
        sv.showEmpty()
    }

    @LayoutRes
    abstract fun getChildLayoutId(): Int

    abstract fun onRetry()

}