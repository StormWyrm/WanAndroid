package com.github.StormWyrm.wanandroid.base.activity

import android.content.Context
import android.os.Bundle
import com.github.StormWyrm.wanandroid.base.mvp.ITopPresenter
import com.github.StormWyrm.wanandroid.base.mvp.ITopView
import com.github.StormWyrm.wanandroid.base.mvp.IView

abstract class BaseMvpActivity<V : ITopView, P : ITopPresenter> : BaseActivity(), IView<P> {

    override fun onCreate(savedInstanceState: Bundle?) {
        bindToPresenter()
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun getContext(): Context? = mActivity

    override fun finishActivity() = finish()

}