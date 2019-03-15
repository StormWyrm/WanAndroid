package com.github.StormWyrm.wanandroid.ui.home

import com.github.StormWyrm.wanandroid.base.mvp.ICollectModel
import com.github.StormWyrm.wanandroid.base.mvp.ICollectPresenter
import com.github.StormWyrm.wanandroid.base.mvp.ICollectView
import com.github.StormWyrm.wanandroid.bean.BannerBean
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.article.ArticleBean
import io.reactivex.Observable

interface HomeContract {
    interface Model : ICollectModel {
        fun reqeustBanner(): Observable<BaseResponse<List<BannerBean>>>

        fun requestArticleList(pageNum: Int): Observable<BaseResponse<ArticleBean>>
    }

    interface View : ICollectView<Presenter> {
        fun onRequestBannerSuccess(bannerList: List<BannerBean>)

        fun onRequestArticleSuccess(articleList: ArticleBean)
    }

    interface Presenter : ICollectPresenter<View, Model> {
        fun requestArticleList(pageNum: Int)

        fun requestBanner()
    }
}