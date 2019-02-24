package com.github.StormWyrm.wanandroid.base.activity

import android.view.View
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.mvp.ILoadView
import com.github.StormWyrm.wanandroid.base.mvp.ITopPresenter
import com.github.StormWyrm.wanandroid.base.mvp.ITopView
import com.github.StormWyrm.wanandroid.base.state.StateView
import kotlinx.android.synthetic.main.activity_base_load.*

abstract class BaseMvpLoadActivity<V : ITopView, P : ITopPresenter> : BaseMvpActivity<V, P>(), ILoadView<P> {
    override val mStateView: StateView by lazy { sv }

    override fun getLayoutId(): Int {
        return R.layout.activity_base_load
    }

    override fun initView() {
        super.initView()
        View.inflate(this, getChildLayoutId(), fl_container)
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

    abstract fun getChildLayoutId(): Int

    abstract fun onRetry()
}