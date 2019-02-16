package com.github.StormWyrm.wanandroid.ui.home

import com.github.StormWyrm.wanandroid.base.fragment.BaseMvpListFragment
import com.github.StormWyrm.wanandroid.bean.BannerBean
import com.github.StormWyrm.wanandroid.bean.article.ArticleBean
import com.orhanobut.logger.Logger

class HomeFragment : BaseMvpListFragment<HomeContract.View, HomeContract.Presenter>(), HomeContract.View {
    override var mPresenter: HomeContract.Presenter = HomePresenter()

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun initLoad() {
        super.initLoad()
        mStateView.showLoading()
        mPresenter.requestBanner()
        mPresenter.requestArticleList()
    }

    override fun onRetry() {
        mPresenter.requestBanner()
        mPresenter.requestArticleList()
    }

    override fun onRequestBannerSuccess(bannerList: List<BannerBean>) {
        mStateView.showSuccess()
        Logger.d(bannerList)
    }

    override fun onRequestArticleSuccess(articleList: ArticleBean) {
        Logger.d(articleList)
    }

}