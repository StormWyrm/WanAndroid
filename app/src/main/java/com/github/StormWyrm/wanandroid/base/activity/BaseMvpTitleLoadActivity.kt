package com.github.StormWyrm.wanandroid.base.activity

import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.mvp.ITopPresenter
import com.github.StormWyrm.wanandroid.base.mvp.ITopView

abstract class BaseMvpTitleLoadActivity<V : ITopView,P : ITopPresenter> : BaseMvpLoadActivity<V,P>(){
    override fun getLayoutId(): Int {
        return R.layout.activity_base_title_load
    }
}