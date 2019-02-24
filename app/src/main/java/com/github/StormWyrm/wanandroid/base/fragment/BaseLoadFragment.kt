package com.github.StormWyrm.wanandroid.base.fragment

import android.view.View
import androidx.annotation.LayoutRes
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.state.StateView
import kotlinx.android.synthetic.main.fragment_base_load.*

abstract class BaseLoadFragment : BaseFragment() {
    val mStateView: StateView by lazy { sv }

    override fun getLayoutId(): Int = R.layout.fragment_base_load

    override fun initView() {
        super.initView()
        View.inflate(mContext, getChildLayoutId(), fl_container)

        sv.onRetry = {
            onRetry()
        }
    }

    fun showLoading() {
        sv.showLoading()
    }

    fun showSuccess() {
        sv.showSuccess()
    }

    fun showError() {
        sv.showError()
    }

    fun showEmpty() {
        sv.showEmpty()
    }

    @LayoutRes
    abstract fun getChildLayoutId(): Int

    abstract fun onRetry()

}