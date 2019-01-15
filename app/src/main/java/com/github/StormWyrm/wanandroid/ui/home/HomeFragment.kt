package com.github.StormWyrm.wanandroid.ui.home

import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.activity.BaseMvpActivity
import kotlinx.android.synthetic.main.fragment_base.*

class HomeFragment : BaseMvpActivity<HomeContract.View,HomeContract.Presenter>(),HomeContract.View {

    override var mPresenter: HomeContract.Presenter = HomePresenter()

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_base

    override fun initView() {
        super.initView()
        tvContent.setText(R.string.main_home_title)
    }

    override fun onRequestBannerError() {
    }

    override fun onRequestBannerSuccess() {
    }

    override fun onRequestArticleSuccess() {
    }

    override fun onRequestArticleError() {
    }
}