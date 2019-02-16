package com.github.StormWyrm.wanandroid.base.fragment

import android.content.Context
import android.os.Bundle
import com.github.StormWyrm.wanandroid.base.mvp.ITopPresenter
import com.github.StormWyrm.wanandroid.base.mvp.ITopView
import com.github.StormWyrm.wanandroid.base.mvp.IView

abstract class BaseMvpFragment<V : ITopView, P : ITopPresenter> : BaseFragment(), IView<P> {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        bindToPresenter()
    }

    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }

    override fun getContext(): Context? = mActivity

    override fun finishActivity() {
        mActivity.finish()
    }
}