package com.github.StormWyrm.wanandroid.ui.home

import com.github.StormWyrm.wanandroid.base.mvp.IListView
import com.github.StormWyrm.wanandroid.base.mvp.IModel
import com.github.StormWyrm.wanandroid.base.mvp.IPresenter
import com.github.StormWyrm.wanandroid.bean.BannerBean
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.article.ArticleBean
import io.reactivex.Observable

interface HomeContract {
    interface Model : IModel {
        fun reqeustBanner(): Observable<BaseResponse<List<BannerBean>>>

        fun requestArticleList(pageNum: Int): Observable<BaseResponse<ArticleBean>>
    }

    interface View : IListView<Presenter> {
        fun onRequestBannerSuccess(bannerList: List<BannerBean>)

        fun onRequestArticleSuccess(articleList: ArticleBean)
    }

    interface Presenter : IPresenter<View, Model> {
        fun requestArticleList(pageNum: Int)

        fun requestBanner()
    }
}