package com.github.StormWyrm.wanandroid.base.activity

import android.view.View
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.state.StateView
import kotlinx.android.synthetic.main.activity_base_load.*

abstract class BaseLoadActivity : BaseActivity() {
    val mStateView: StateView by lazy { sv }

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

    abstract fun getChildLayoutId(): Int

    abstract fun onRetry()

}