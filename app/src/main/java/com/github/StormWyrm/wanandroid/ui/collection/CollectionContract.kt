package com.github.StormWyrm.wanandroid.ui.collection

import com.github.StormWyrm.wanandroid.base.mvp.IListView
import com.github.StormWyrm.wanandroid.base.mvp.IModel
import com.github.StormWyrm.wanandroid.base.mvp.IPresenter
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.article.ArticleBean
import io.reactivex.Observable

interface CollectionContract {
    interface Model : IModel {
        fun requestCollectionList(pageNum: Int): Observable<BaseResponse<ArticleBean>>

        fun requestRemoveMyCollection(id: Int, originId: Int):Observable<BaseResponse<String>>
    }

    interface Presenter : IPresenter<View, Model> {
        fun requestCollectionList(pageNum: Int)

        fun requestRemoveMyCollection(id: Int, originId: Int, position: Int)
    }

    interface View : IListView<Presenter> {
        fun onRequestCollectionListSuccess(articleList: ArticleBean)

        fun onRemoveMyCollectionSuccess(position: Int)
    }
}
