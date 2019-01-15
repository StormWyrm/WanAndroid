package com.github.StormWyrm.wanandroid.ui.home

import com.github.StormWyrm.wanandroid.base.mvp.IModel
import com.github.StormWyrm.wanandroid.base.mvp.IPresenter
import com.github.StormWyrm.wanandroid.base.mvp.IView
import com.github.StormWyrm.wanandroid.bean.BannerBean
import com.shehuan.wanandroid.bean.article.ArticleBean
import io.reactivex.Observable

interface HomeContract {
    interface Model : IModel{
//        fun reqeustBanner() : Observable<List<BannerBean>>
//
//        fun requestArticleList(pageNum : Int) : Observable<ArticleBean>
    }

    interface View : IView<Presenter>{
        fun  onRequestBannerError()

        fun onRequestBannerSuccess()

        fun onRequestArticleSuccess()

        fun onRequestArticleError()
    }

    interface Presenter : IPresenter<View, Model>{
        fun requestArticleList()

        fun requestBanner()
    }
}