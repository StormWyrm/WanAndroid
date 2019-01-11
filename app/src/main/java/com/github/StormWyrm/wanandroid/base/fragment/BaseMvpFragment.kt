package com.github.StormWyrm.wanandroid.base.fragment

import android.os.Bundle
import com.github.StormWyrm.wanandroid.base.mvp.ITopPresenter
import com.github.StormWyrm.wanandroid.base.mvp.ITopView
import com.github.StormWyrm.wanandroid.base.mvp.IView

abstract class BaseMvpFragment<V : ITopView,P : ITopPresenter> : BaseFragment() ,IView<P>{
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        bindToPresenter()
        super.onActivityCreated(savedInstanceState)
    }

    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }
}